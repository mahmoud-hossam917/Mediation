package com.memory.Mediation.Models;


import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;

@Setter
@Getter
@Builder
@Entity(name = "cdr_data")

public class CDR {
    public String getA_Sub() {
        return a_Sub;
    }

    public void setA_Sub(String a_Sub) {
        this.a_Sub = a_Sub;
    }

    public void setB_Sub(String b_Sub) {
        this.b_Sub = b_Sub;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public CDR(int id, String a_Sub, String b_Sub, int duration) {
        this.id = id;
        this.a_Sub = a_Sub;
        this.b_Sub = b_Sub;
        this.duration = duration;
    }

    public String getB_Sub() {
        return b_Sub;
    }

    public int getDuration() {
        return duration;
    }

    public CDR(String a_Sub, String b_Sub, int duration) {
        this.a_Sub = a_Sub;
        this.b_Sub = b_Sub;
        this.duration = duration;
    }
    public CDR(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(name = "cdr_data_id_seq", sequenceName = "cdr_data_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cdr_data_id_seq")
    private int id;
    private String a_Sub;
    private String b_Sub;
    private int duration;


}
