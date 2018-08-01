package com.example.eurekaconsumer;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client")
public interface ClientService {

    @GetMapping("/consumer")
    String consumer();

}
