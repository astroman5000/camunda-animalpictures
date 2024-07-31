package com.camunda.animalpicture.db;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class DBConnector {

    private Connection c = null;

    public DBConnector(){

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/pictures",
                            "camunda", "camunda");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        createTable();
    }

    public void createTable(){
        try {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS pictures " +
                    "(id VARCHAR(100) PRIMARY KEY     NOT NULL," +
                    " imagebytes           BYTEA    NOT NULL)";
            stmt.execute(sql);
            System.out.println("Created table pictures successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertImageRecord(String id, byte[] image){
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement("INSERT INTO pictures VALUES (?, ?)");
            ps.setString(1, id);
            ps.setBinaryStream(2, new ByteArrayInputStream(image));
            ps.executeUpdate();
            System.out.println("Inserted picture successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
