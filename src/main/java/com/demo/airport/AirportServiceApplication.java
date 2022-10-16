package com.demo.airport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
    exclude = {
        SecurityAutoConfiguration.class
    })
public class AirportServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(AirportServiceApplication.class, args);
    }

}
