package com.example.markable.footballapptest.Classes;

import java.io.Serializable;

public class NextMatches implements Serializable {

    private String nameDivision;
    private int idTour;
    private String teamHome;
    private String teamVisit;
    private String date;
    private String nameStadium;

    public NextMatches(String nameDivision, int idTour, String teamHome, String teamVisit, String date, String nameStadium) {
        this.nameDivision = nameDivision;
        this.idTour = idTour;
        this.teamHome = teamHome;
        this.teamVisit = teamVisit;
        this.date = date;
        this.nameStadium = nameStadium;
    }

    public String getNameDivision() {
        return nameDivision;
    }

    public int getIdTour() {
        return idTour;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public String getTeamVisit() {
        return teamVisit;
    }

    public String getDate() {
        return date;
    }

    public String getNameStadium() {
        return nameStadium;
    }

    @Override
    public String toString() {
        return "NextMatches{" +
                "nameDivision='" + nameDivision + '\'' +
                ", idTour=" + idTour +
                ", teamHome='" + teamHome + '\'' +
                ", teamVisit='" + teamVisit + '\'' +
                ", date='" + date + '\'' +
                ", nameStadium='" + nameStadium + '\'' +
                "}\n";
    }
}
