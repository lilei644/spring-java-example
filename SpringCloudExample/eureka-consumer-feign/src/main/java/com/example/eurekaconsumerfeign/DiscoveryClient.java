package com.example.eurekaconsumerfeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client")
public interface DiscoveryClient {

    @GetMapping("/dc")
    String consumer();

}
