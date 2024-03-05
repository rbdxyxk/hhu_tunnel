package com.suidao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.suidao.mapper")
@EnableTransactionManagement
public class SuidaoMain9000 {
    public static void main(String[] args) {
        SpringApplication.run(SuidaoMain9000.class,args);
    }
}
