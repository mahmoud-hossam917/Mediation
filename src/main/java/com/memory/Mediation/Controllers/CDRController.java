package com.memory.Mediation.Controllers;

import com.memory.Mediation.Services.InsertService;
import com.memory.Mediation.Services.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CDRController {

    private final InsertService insertService;
    private final ReadService readService;

    @Autowired
    public CDRController(ReadService readService, InsertService insertService) {
        this.readService = readService;
        this.insertService = insertService;
    }

    @GetMapping("insert")
    @Scheduled(fixedRate = 60000)
    public ResponseEntity insertIntoCDR() {

        System.out.println("another insert will realse");
        readService.doWork();
        return ResponseEntity.ok("ok");
    }

    @GetMapping("clear")
    @Scheduled(fixedRate = 500)
    public ResponseEntity clearList() {

        insertService.doWork();
        return ResponseEntity.ok("ok");
    }


}
