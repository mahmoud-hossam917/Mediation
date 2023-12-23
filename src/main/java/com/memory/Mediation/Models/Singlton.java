package com.memory.Mediation.Models;


import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public enum Singlton {

    INSTANSE;

    public final BlockingQueue<List<CDR>> queueOfListsOfCDR = new ArrayBlockingQueue<>(40);


}
