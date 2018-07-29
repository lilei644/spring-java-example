package com.example.eurekaconsumerribbonhystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DiscoveryService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    public String consumer() {
        return restTemplate.getForObject("http://eureka-client/dc", String.class);
    }


    /**
     * 加载超时时执行的方法
     *
     * @return
     */
    private String fallbackMethod() {
        return "fallback";
    }

}
