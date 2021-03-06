package com.example.markable.footballapptest.Classes;

import java.io.Serializable;

public class NextMatches extends NextMatchesWithGoal implements Serializable {

    private String nameDivision;
    private int idMatch;
    private int idDivision;
    private int idTour;
    private String teamHome;
    private String teamVisit;
    private String date;
    private String nameStadium;
    String imageHome;
    String imageGuest;

    public NextMatches(String nameDivision, int idTour, String teamHome, String teamVisit, String date, String nameStadium) {
        super();
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setNameStadium(String nameStadium) {
        this.nameStadium = nameStadium;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public int getIdDivision() {
        return idDivision;
    }

    public String getImageHome() {
        return imageHome;
    }

    public String getImageGuest() {
        return imageGuest;
    }

    @Override
    public String toString() {
        return "NextMatches{" +
                "nameDivision='" + nameDivision + '\'' +
                ", idMatch=" + idMatch +
                ", idDivision=" + idDivision +
                ", idTour=" + idTour +
                ", teamHome='" + teamHome + '\'' +
                ", teamVisit='" + teamVisit + '\'' +
                ", date='" + date + '\'' +
                ", nameStadium='" + nameStadium + '\'' +
                '}' + " " + super.toString() + "\n";
    }
}
