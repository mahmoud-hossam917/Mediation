package com.memory.Mediation.Models;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Singlton {

    INSTANSE;
    private final List<CDR> allRecords = new ArrayList<>();

    public final List<CDR> synchronizedList = Collections.synchronizedList(allRecords);


}
