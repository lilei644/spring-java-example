package com.lilei.demo.springmultipledata;

import com.lilei.demo.springmultipledata.config.db.DBConfig1;
import com.lilei.demo.springmultipledata.config.db.DBConfig2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = { DBConfig1.class, DBConfig2.class })
public class SpringmultipledataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringmultipledataApplication.class, args);
    }

}
