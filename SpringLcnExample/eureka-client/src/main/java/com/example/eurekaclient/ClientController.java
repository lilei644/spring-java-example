package com.example.eurekaclient;

import com.example.eurekaclient.impl.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/consumer")
    public String consumer() {
        return clientService.consumer();
    }

}
