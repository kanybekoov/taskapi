package com.example.taskapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ExternalApiController {
    private static final Logger log = LoggerFactory.getLogger(ExternalApiController.class);

    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/external")
    public String callExternalApi() {
        String url = "https://api.restful-api.dev/objects";
        String response = restTemplate.getForObject(url, String.class);

        log.info("External API called: {} Response: {}", url, response);

        return "Response logged into file ./logs/app.log!";
    }
}
