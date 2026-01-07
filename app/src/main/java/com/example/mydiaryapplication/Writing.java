package com.example.mydiaryapplication;

import java.io.Serializable;
import java.time.LocalDate;

public class Writing implements Serializable {
    int id;
    String text;
    LocalDate date;
    public Writing(String text, LocalDate date, int id){
        this.id = id;
        this.date = date;
        this.text = text;
    }

    public Writing(){

    }

    @Override
    public String toString() {
        return "Writing{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
