package org.m2tnsi.cs1.classes;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Global implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public int totalConfirmed;
    public int newDeaths;
    public int totalDeaths;
    public int newRecovered;
    public int totalRecovered;
    public Timestamp dateMaj;

    public Global() {}

    public Global(int totalConfirmed, int newDeaths, int totalDeaths, int newRecovered,
                  int totalRecovered, Timestamp dateMaj) {
        this.totalConfirmed = totalConfirmed;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
        this.newRecovered = newRecovered;
        this.totalRecovered = totalRecovered;
        this.dateMaj = dateMaj;
    }

    public int getId(){
        return id;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(int newRecovered) {
        this.newRecovered = newRecovered;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public Timestamp getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(Timestamp dateMaj) {
        this.dateMaj = dateMaj;
    }
}
