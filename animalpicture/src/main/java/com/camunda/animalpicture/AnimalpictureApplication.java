package com.camunda.animalpicture;

import java.util.HashMap;
import java.util.Map;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.api.response.ActivatedJob;

@SpringBootApplication
public class AnimalpictureApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalpictureApplication.class, args);
	}

}
