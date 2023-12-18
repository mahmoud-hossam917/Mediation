package com.memory.Mediation.Controllers;

import com.memory.Mediation.Models.CDR;
import com.memory.Mediation.Services.CDRService;
import com.memory.Mediation.Services.InsertService;
import com.memory.Mediation.Services.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CDRController {

    private InsertService insertService;
    private ReadService readService;

    private int value=0;
    @Autowired
    public CDRController(ReadService readService , InsertService insertService){
        this.readService=readService;
        this.insertService = insertService;
    }

    @GetMapping("insert")
    @Scheduled(fixedRate = 60000)
    public ResponseEntity insertIntoCDR(){

        System.out.println("another insert will realse");
        readService.dowork();
        return  ResponseEntity.ok("ok");
    }

    @GetMapping("clear")
    @Scheduled(fixedRate = 2000)
    public ResponseEntity clearList(){

//        System.out.println("another thread will realse");
        insertService.doWork();
        return ResponseEntity.ok("ok");
    }


//    @GetMapping("ins")
//    public ResponseEntity insertData(){
//
//        CDR cdr=new CDR("a","b",6);
//        cdrService.insertData(cdr);
//        return ResponseEntity.ok("ok");
//    }

}
