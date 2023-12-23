package com.memory.Mediation.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class ReadService implements Runnable {

    private final CDRService cdrService;
    private final ExecutorService readExecutorService;

    @Autowired
    public ReadService(CDRService cdrService,
                       @Qualifier("readExecutorService") ExecutorService readExecutorService) {
        this.cdrService = cdrService;
        this.readExecutorService = readExecutorService;

    }

    @Override
    public void run() {

        synchronized (this) {
            cdrService.readCDR();
        }

    }

    public void doWork() {
        System.out.println("this is the doWork for insert");
        readExecutorService.submit(this);
    }
}
