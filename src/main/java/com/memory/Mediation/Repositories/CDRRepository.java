package com.memory.Mediation.Repositories;

import com.memory.Mediation.Models.CDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CDRRepository extends JpaRepository<CDR, Integer> {


}
