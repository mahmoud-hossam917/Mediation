package com.memory.Mediation.Models;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public enum Singlton {

    INSTANSE;

    public final BlockingQueue<List<CDR>> queueOfListsOfCDR=new ArrayBlockingQueue<>(40);


}
