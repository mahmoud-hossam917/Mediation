package com.memory.Mediation.Services;


import com.memory.Mediation.Repositories.CDRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.memory.Mediation.Services.Singlton.INSTANSE;
@Service
public class InsertService implements Runnable{

    private CDRService cdrService;

    @Autowired
    public InsertService(CDRService cdrService){

        this.cdrService=cdrService;

    }



    @Override
    public void run(){

        synchronized (this) {
            cdrService.clearList();
        }
    }

    public void doWork(){
        Thread thread=new Thread(this);
        thread.start();

    }

}




