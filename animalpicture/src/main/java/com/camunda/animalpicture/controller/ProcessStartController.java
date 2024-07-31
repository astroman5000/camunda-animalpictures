package com.camunda.animalpicture.controller;

import com.camunda.animalpicture.worker.CamundaWorker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessStartController {
    @RequestMapping(path="/start", method=RequestMethod.POST)
    public String startProcess(@RequestParam String animalType){

        CamundaWorker worker = new CamundaWorker();
        worker.startProcess(animalType);

        return animalType;
    }
}
