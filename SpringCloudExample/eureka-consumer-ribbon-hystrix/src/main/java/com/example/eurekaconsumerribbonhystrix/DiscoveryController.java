package com.example.eurekaconsumerribbonhystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DiscoveryController {

    @Autowired
    private DiscoveryService discoveryService;

    @GetMapping("/consumer")
    public String dc() {
        return discoveryService.consumer();
    }

}
