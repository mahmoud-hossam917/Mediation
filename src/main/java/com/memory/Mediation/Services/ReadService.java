package com.memory.Mediation.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadService implements Runnable {

    private final CDRService cdrService;

    @Autowired
    public ReadService(CDRService cdrService) {
        this.cdrService = cdrService;
    }

    @Override
    public void run() {

        synchronized (this) {
            cdrService.readCDR();
        }

    }

    public void doWork() {
        System.out.println("this is the doWork for insert");
        Thread thread = new Thread(this);
        thread.start();
    }
}
