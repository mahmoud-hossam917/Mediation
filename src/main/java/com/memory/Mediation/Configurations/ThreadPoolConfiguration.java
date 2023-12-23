package com.memory.Mediation.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfiguration {


    @Bean(name = "readExecutorService")
    public ExecutorService readExecutorService() {
        return Executors.newFixedThreadPool(1);
    }

    @Bean(name = "insertExecutorService")
    public ExecutorService insertExecutorService() {
        return Executors.newFixedThreadPool(3);
    }

}
