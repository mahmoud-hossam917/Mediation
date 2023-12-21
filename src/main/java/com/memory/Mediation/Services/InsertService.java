package com.memory.Mediation.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertService implements Runnable {

    private CDRService cdrService;

    @Autowired
    public InsertService(CDRService cdrService) {

        this.cdrService = cdrService;

    }


    @Override
    public void run() {

        synchronized (this) {
            cdrService.clearList();
        }
    }

    public void doWork() {
        Thread thread = new Thread(this);
        thread.start();

    }

}




