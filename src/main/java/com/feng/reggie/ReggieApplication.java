package com.feng.reggie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author f
 * @date 2023/4/25 21:43
 */
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        System.out.println("====================================================================");
        System.out.println("                               start success                        ");
        System.out.println("====================================================================");
    }
}
