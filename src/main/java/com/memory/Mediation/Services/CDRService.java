package com.memory.Mediation.Services;


import com.memory.Mediation.Models.CDR;
import com.memory.Mediation.Repositories.CDRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import static com.memory.Mediation.Services.Singlton.INSTANSE;

@Service
public class CDRService {




    private CDRRepository cdrRepository;

    @Autowired
    public CDRService(CDRRepository repository){
        this.cdrRepository=repository;
    }

    public void insertData(CDR cdr){
        cdrRepository.save(cdr);
    }


    public synchronized void lineForRecord(String line){
        try {
            String[] records = line.split(",");
            CDR cdr = new CDR(records[0], records[1], Integer.parseInt(records[2]));


            insertIntoList(cdr);


            while (INSTANSE.synchronizedList.size() >= 50000) {
                System.out.println("LIST SIZE IS:" + INSTANSE.synchronizedList.size());
                wait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public synchronized void readCDR(){

        Date date =new Date();
        System.out.println("The engine statred on: "+date);
        Path folderPath=Paths.get("E:\\test\\CDR");

        try(DirectoryStream<Path> directoryStream= Files.newDirectoryStream(folderPath,"*.txt")){

            for(Path filePath :directoryStream){

                System.out.println(filePath.getFileName()+" is started");
                try{

                    Files.lines(filePath).forEach(line->{

                      lineForRecord(line);
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }

                Path newFilePath = filePath.resolveSibling(filePath.getFileName().toString().replace(".txt", ".parsed"));
                Files.move(filePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(filePath.getFileName()+" is done");

            }
            wait();
            Date date2 = new Date();
            System.out.println("The engine finished on: "+date2);

        }catch (Exception ex){
            ex.printStackTrace();
        }
//
//        try{
//
//
//            File folder=new File("E:\\test\\CDR");
//
//            File[] files=folder.listFiles();
//
//            if(files!=null){
//
//                for(File file : files){
//
//                    if(file.isFile()&&file.getName().endsWith(".txt")){
//
//
//                        List<String> data= Files.readAllLines(file.toPath());
//                        CDR cdr=new CDR();
//                        System.out.println("this is the size of file: "+data.size());
//                        for(String line:data){
//
//
//                            String[] records=line.split(",");
//                            cdr=new CDR(records[0],records[1],Integer.parseInt(records[2]));
////                            System.out.println("it goes to insert");
//                            insertIntoList(cdr);
//                            while(INSTANSE.synchronizedList.size()>=50000){
//                                System.out.println("LIST SIZE IS:"+INSTANSE.synchronizedList.size());
//                                wait();
//                            }
////                            System.out.println("it back from insert to the next step----");
//
//                        }
//                        Path path=file.toPath();
//                        Path newFilePath=Paths.get(path.toString().replace(".txt",".parsed"));
//                        Files.move(path,newFilePath, StandardCopyOption.REPLACE_EXISTING);
//
//                        System.out.println(file.getName()+" is done");
//
//                    }
//
//
//                }
//
//
//            }
//
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
    }


    public synchronized   void insertIntoList(CDR cdr) throws InterruptedException {

        synchronized (INSTANSE.synchronizedList){

            INSTANSE.synchronizedList.add(cdr);
        }
    }



    public synchronized  void clearList(){

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

    public void putIntoDataBase(){
        System.out.println("what happend here");
        cdrRepository.saveAll(INSTANSE.synchronizedList);
        System.out.println("it is inserted successful");
    }


}
