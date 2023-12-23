package com.memory.Mediation.Services;


import com.memory.Mediation.Models.CDR;
import com.memory.Mediation.Repositories.CDRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.memory.Mediation.Models.Singlton.INSTANSE;

@Service
public class CDRService {


    private CDRRepository cdrRepository;
    private List<CDR> records;

    @Autowired
    public CDRService(CDRRepository repository) {
        this.cdrRepository = repository;
    }

    public List<CDR> getNewList() {
        return new ArrayList<>();
    }
    /*
     You have two methods: 'readFilesUsingNIO,' which uses Java NIO,
      and the second one, 'readAllLinesOfFile,'
      which uses the 'readAllLines' function to load the entire file  into memory.
     */

    // Note: don't forget to change the path of folder which the engine will read from it
    public synchronized void readCDR() {

        readFiles();
        Date enddate = new Date();
        System.out.println("end Date: " + enddate);
    }
    public void insertDataIntoDataBase(List<CDR> data){
        System.out.println("ready to insert into database");
        cdrRepository.saveAll(data);
        System.out.println("data inserted successfully");
        Date date=new Date();
        System.out.println("data inserted on: "+date);
    }
    public void takeListFromQueue() throws InterruptedException {

        if(!INSTANSE.queueOfListsOfCDR.isEmpty()) {
            List<CDR> data = INSTANSE.queueOfListsOfCDR.take();
            insertDataIntoDataBase(data);
            data = null;
        }
    }
    public void createCDR(String line) {

        try {
            String[] records = line.split(",");
            CDR cdr = new CDR(records[0], records[1], Integer.parseInt(records[2]));

            insertRecord(cdr);

        } catch (Exception e) {
            e.fillInStackTrace();
        }

    }

    public void insertRecord(CDR cdr) throws InterruptedException {

        records.add(cdr);
        if (records.size() >= 50000)
            insertIntoQueue();

    }

    public void insertIntoQueue() throws InterruptedException {
        if (!records.isEmpty()) {
            System.out.println("50000 records inserted into queue");
            INSTANSE.queueOfListsOfCDR.put(records);
            System.out.println(INSTANSE.queueOfListsOfCDR.size());
            records = getNewList();
        }
        checkSizeOfQueue();
    }
    public void checkSizeOfQueue() throws InterruptedException {
        while (INSTANSE.queueOfListsOfCDR.size()>=10){
            wait(50);
        }
    }

    public void readFiles() {

        Date date = new Date();
        System.out.println("The engine statred on: " + date);
        Path folderPath = Paths.get("E:\\test\\CDR");

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath, "*.txt")) {

            records = getNewList();
            for (Path filePath : directoryStream) {

                System.out.println(filePath.getFileName() + " is started");
                try {

                    Files.lines(filePath).forEach(this::createCDR);

                } catch (Exception e) {
                    e.fillInStackTrace();
                }

                changeExtension(filePath);
                System.out.println(filePath.getFileName() + " is done");

            }
            insertIntoQueue();
            Date date2 = new Date();
            System.out.println("The engine finished on: " + date2);

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }


    }

    public void changeExtension(Path filePath) throws IOException {
        Path newFilePath = filePath.resolveSibling(filePath.getFileName().toString().
                replace(".txt", ".parsed"));
        Files.move(filePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
    }


}
