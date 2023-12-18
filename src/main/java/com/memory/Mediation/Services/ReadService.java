package com.memory.Mediation.Services;

import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.Mediation.Services.Singlton.INSTANSE;

@Service
public class ReadService implements  Runnable{

    private CDRService cdrService;

    @Autowired
    public ReadService(CDRService cdrService){
        this.cdrService=cdrService;
    }

    @Override
    public void run() {
        synchronized (this) {
            cdrService.readCDR();
        }
    }

    public void dowork(){
        System.out.println("this is the doWork for insert");
        Thread thread=new Thread(this);
        thread.start();
    }
}
