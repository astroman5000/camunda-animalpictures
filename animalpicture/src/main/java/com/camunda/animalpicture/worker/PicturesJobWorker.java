package com.camunda.animalpicture.worker;

import java.util.HashMap;
import java.util.Map;

import com.camunda.animalpicture.db.DBConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.apache.hc.client5.http.entity.mime.ByteArrayBody;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class PicturesJobWorker {
    DBConnector dbConnector = null;
    public PicturesJobWorker(){
        dbConnector = new DBConnector();
    }

    @JobWorker(type = "get-dog-picture")
    public Map<String, Object> getDocPicture(final ActivatedJob job) {

        String jobVariables = job.getVariables();
        System.out.println("PicturesJobWorker: getDogPicture: " + jobVariables);

        String animalType = getAnimalType(jobVariables);

        byte[] imageBytes = getPicture(animalType);

        saveToDatabase(job.getProcessInstanceKey(),imageBytes);

        // Probably add some process variables
        HashMap<String, Object> variables = new HashMap<>();
        //variables.put("images-bytes", imageBytes);
        return variables;
    }

    @JobWorker(type = "get-cat-picture")
    public Map<String, Object> getCatPicture(final ActivatedJob job) {

        String jobVariables = job.getVariables();
        System.out.println("PicturesJobWorker: getCatPicture: " + jobVariables);

        String animalType = getAnimalType(jobVariables);

        byte[] imageBytes = getPicture(animalType);

        saveToDatabase(job.getProcessInstanceKey(),imageBytes);

        // Probably add some process variables
        HashMap<String, Object> variables = new HashMap<>();
        //variables.put("images-bytes", imageBytes);
        return variables;
    }

    @JobWorker(type = "get-bear-picture")
    public Map<String, Object> getBearPicture(final ActivatedJob job) {
        String jobVariables = job.getVariables();
        System.out.println("PicturesJobWorker: getBearPicture: " + jobVariables);

        String animalType = getAnimalType(jobVariables);

        byte[] imageBytes = getPicture(animalType);

        saveToDatabase(job.getProcessInstanceKey(),imageBytes);

        // Probably add some process variables
        HashMap<String, Object> variables = new HashMap<>();
        //variables.put("image_bytes", imageBytes);
        return variables;
    }

    private byte[] getPicture(String animalType){
        String baseURL = "";
        if(animalType.equals("cat")){
            baseURL = "https://cataas.com/cat?width=300&height=400";
        }
        else if(animalType.equals("dog")){
            baseURL = "https://place.dog/300/200";
        }
        else if(animalType.equals("bear")){
            baseURL = "https://placebear.com/200/300";
        }

        String base64 = "";

        RestClient restClient = RestClient.create();

        byte[] imageBytes = restClient.get()
                .uri(baseURL)
                .retrieve()
                .body(byte[].class);

        System.out.println(imageBytes);

        return imageBytes;
    }

    private String getAnimalType(String jobVariables) {
        String animalType = "";
        JsonNode node = null;
        try {
            node = new ObjectMapper().readTree(jobVariables);
            if (node.has("selected_animal")) {
                animalType = node.get("selected_animal").textValue();
                System.out.println("selected_animal: " + animalType);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return animalType;
    }

    private void saveToDatabase(long jobid, byte[] imageBytes){
        //store to postgresql
        dbConnector.insertImageRecord(Long.toString(jobid), imageBytes);
    }
}
