package com.camunda.animalpicture.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;

@SpringBootApplication
@Deployment(resources = "classpath:AnimalPictureProcess.bpmn")
public class CamundaWorker{

    @Autowired
    private ZeebeClient zeebeClient = ZeebeClient.newClient();

    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public CamundaWorker(){
        logger.setLevel(Level.ALL);
    }

    public long startProcess(String animalType){
        var processDefinitionKey = "animal-picture";
        var event = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(processDefinitionKey)
                .latestVersion()
                .variables(Map.of("selected_animal", animalType))
                .send()
                .join();
        logger.info(String.format("started a process: %d", event.getProcessInstanceKey()));

        return event.getProcessInstanceKey();
    }

}
