package com.example.markable.footballapptest.Classes;

import java.io.Serializable;

public class PrevMatches implements Serializable {
    private String nameDivision;
    private int idTour;
    private String teamHome;
    private int goalHome;
    private int goalVisit;
    private String teamVisit;

    public PrevMatches(String nameDivision, int idTour, String teamHome, int goalHome, int goalVisit, String teamVisit) {
        this.nameDivision = nameDivision;
        this.idTour = idTour;
        this.teamHome = teamHome;
        this.goalHome = goalHome;
        this.goalVisit = goalVisit;
        this.teamVisit = teamVisit;
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

    public int getGoalHome() {
        return goalHome;
    }

    public int getGoalVisit() {
        return goalVisit;
    }

    public String getTeamVisit() {
        return teamVisit;
    }

    @Override
    public String toString() {
        String result = nameDivision + " " + idTour + " " + teamHome + " " + goalHome + " " + goalVisit + " " + teamVisit;
        return result;
    }
}
