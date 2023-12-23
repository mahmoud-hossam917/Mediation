package com.memory.Mediation.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class InsertService implements Runnable {

    private final CDRService cdrService;
    private final ExecutorService insertExecutorService;

    @Autowired
    public InsertService(CDRService cdrService, @Qualifier("insertExecutorService") ExecutorService insertExecutorService) {
        this.insertExecutorService = insertExecutorService;
        this.cdrService = cdrService;

    }


    @Override
    public void run() {

        try {
            cdrService.takeListFromQueue();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void doWork() {
        insertExecutorService.submit(this);

    }

}




