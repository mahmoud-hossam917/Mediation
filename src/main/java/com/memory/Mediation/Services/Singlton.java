package com.memory.Mediation.Services;


import com.memory.Mediation.Models.CDR;
import com.memory.Mediation.Repositories.CDRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Singlton {

   INSTANSE;
   private   List<CDR> allRecords =new ArrayList<>();

   public   List<CDR> synchronizedList= Collections.synchronizedList(allRecords);






}
