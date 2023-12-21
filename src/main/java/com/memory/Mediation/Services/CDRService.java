package com.memory.Mediation.Services;


import com.memory.Mediation.Models.CDR;
import com.memory.Mediation.Repositories.CDRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

import static com.memory.Mediation.Models.Singlton.INSTANSE;

@Service
public class CDRService {


    private CDRRepository cdrRepository;

    @Autowired
    public CDRService(CDRRepository repository) {
        this.cdrRepository = repository;
    }

    public void insertData(CDR cdr) {
        cdrRepository.save(cdr);
    }

    /*
     You have two methods: 'readFilesUsingNIO,' which uses Java NIO,
      and the second one, 'readAllLinesOfFile,'
      which uses the 'readAllLines' function to load the entire file  into memory.
     */

    // Note: don't forget to change the path of folder which the engine will read from it
    public synchronized void readCDR() {

        readFilesUsingNIO();
//        readAllLinesOfFile();
        Date enddate = new Date();
        System.out.println("end Date: " + enddate);
    }

    public void readFilesUsingNIO() {


        Date date = new Date();
        System.out.println("The engine statred on: " + date);
        Path folderPath = Paths.get("E:\\test\\CDR");

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath, "*.txt")) {

            for (Path filePath : directoryStream) {

                System.out.println(filePath.getFileName() + " is started");
                try {

                    Files.lines(filePath).forEach(this::putRecordIntoCDRModel);

                } catch (Exception e) {
                    e.fillInStackTrace();
                }

                changeExtension(filePath);
                System.out.println(filePath.getFileName() + " is done");

            }
            wait();
            Date date2 = new Date();
            System.out.println("The engine finished on: " + date2);

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

    }

    public void readAllLinesOfFile() {


        try {

            Date startDate = new Date();
            System.out.println("start Date is: " + startDate);
            File folder = new File("E:\\test\\CDR");
            File[] files = folder.listFiles();
            if (files == null) return;

            for (File file : files) {

                if (file.isFile() && file.getName().endsWith(".txt")) {

                    List<String> data = Files.readAllLines(file.toPath());
                    System.out.println("this is the size of file: " + data.size());
                    for (String line : data) {

                        putRecordIntoCDRModel(line);
                    }
                    changeExtension(file.toPath());
                    System.out.println(file.getName() + " is done");

                }


            }
            wait();
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

    }

    public synchronized void putRecordIntoCDRModel(String line) {
        try {
            String[] records = line.split(",");
            CDR cdr = new CDR(records[0], records[1], Integer.parseInt(records[2]));
            insertIntoList(cdr);
            checkIfSizeOfListExceedTheLimit();
        } catch (Exception e) {
            e.fillInStackTrace();
        }

    }


    public void checkIfSizeOfListExceedTheLimit() throws InterruptedException {
        while (INSTANSE.synchronizedList.size() >= 50000) {
            System.out.println("LIST SIZE IS:" + INSTANSE.synchronizedList.size());
            wait();
        }
    }

    public synchronized void insertIntoList(CDR cdr) throws InterruptedException {

        synchronized (INSTANSE.synchronizedList) {

            INSTANSE.synchronizedList.add(cdr);
        }
    }

    public synchronized void clearList() {

        synchronized (INSTANSE.synchronizedList) {
            if (!INSTANSE.synchronizedList.isEmpty()) {
                System.out.println("Size of list:::: " + INSTANSE.synchronizedList.size());
                putIntoDataBase();
                INSTANSE.synchronizedList.clear();
                System.out.println("list cleared::::");

            }
            notifyAll();
        }
    }

    public void putIntoDataBase() {
        System.out.println("what happend here");
        cdrRepository.saveAll(INSTANSE.synchronizedList);
        System.out.println("it is inserted successful");
    }

    public void changeExtension(Path filePath) throws IOException {
        Path newFilePath = filePath.resolveSibling(filePath.getFileName().toString().
                replace(".txt", ".parsed"));
        Files.move(filePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
    }


}
