package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SpringBootApplication
public class WebApplication {

    private static Logger LOGGER;

    static {
        try(FileInputStream ins = new FileInputStream("log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(WebApplication.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        LOGGER.info("Started application");
    }

}
