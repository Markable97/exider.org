package com.example.markable.footballapptest.Classes;

import java.io.Serializable;

public class PrevMatches implements Serializable {
    int idMatch;
    String nameDivision;
    int idTour;
    String teamHome;
    int goalHome;
    int goalVisit;
    String teamVisit;

    public PrevMatches(int idMatch, String nameDivision, int idTour, String teamHome, int goalHome, int goalVisit, String teamVisit) {
        this.idMatch = idMatch;
        this.nameDivision = nameDivision;
        this.idTour = idTour;
        this.teamHome = teamHome;
        this.goalHome = goalHome;
        this.goalVisit = goalVisit;
        this.teamVisit = teamVisit;
    }

    public int getIdMatch() {
        return idMatch;
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
        String result =idMatch + " " + nameDivision + " " + idTour + " " + teamHome + " " + goalHome + " " + goalVisit + " " + teamVisit;
        return result;
    }
}
