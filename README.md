## Camunda Tech Challenge for Consulting Submission
### Author: Andrew Palizzi, andrew.palizzi@gmail.com

## Project Intro
This is a project to retrieve animal images from pubic REST APIs. The solutions utilizes a business process developed on the Camunda SAAS and a client Java application.

## BPMN Process
Name: AnimalPictureProcess.bpmn

Input: JSON string to pass in selected_animal, valid values are "cat", "dog", "bear"

Example Input: {"selected_animal":"dog"}

## Client Java Application
The client Java application is used to initiate the Animal Pictures process using a REST endpoint. It also implements a JobWorker to download the animal pictures using RESTClient.

Requirements
- Java JDK 21
- PostgreSQL database server

### Import Classes
- AnimalpictureApplication -  a SpringBootApplication app and is the main entry point to run the application
- ProcessStartController - the RestController class for the /start endpoint
- CamundaWorkder - a Zeebe client which interacts with the Camunda SAAS cluster
- PicturesJobWorker - a JobWorker class which handles the service tasks for retrieving the pictures
- DBConnector - utility class used by the PicturesJobWorker to store downloaded pictures to a PostgreSQL database


### REST Endpoint
Method: POST

URL: /start

Parameters: animalType (string with value = "dog","cat","bear")

example: http://localhost8080/start/animalType=dog


## Known Issues
Note, I ran out time implementing and troubleshooting all of the features of the solution. The following are the know issues.
- /start REST endpoint returns a server 500 error, the underlying exception java.net.ConnectException: Connection refused: getsockopt. Due to this, the process can currently only be initiated through the Camunda SAAS modeler.
- DBConnector class is a workaround due to encountering some issues with Spring Boot JPA Starter and the jakarta.persistence package
- This application is not containerized
- There are no automated tests for this solution. I would have used JUnit to add test cases for the client application.
- Both cat pictures sources that I looked seem to be down currently, cataas.com and placekitten.com
