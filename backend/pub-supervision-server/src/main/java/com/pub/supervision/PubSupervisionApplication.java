package com.pub.supervision;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pub.supervision.mapper")
public class PubSupervisionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubSupervisionApplication.class, args);
    }
}
