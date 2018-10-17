package com.example.markable.footballapptest.Classes;

import java.io.Serializable;

public class AllMatchesForTeam implements Serializable{

    PrevMatches matches;
    String urlImageHome;
    String urlImageGuest;

    public AllMatchesForTeam(String nameDivision, int idTour, String teamHome,
                                 int goalHome, int goalVisit, String teamVisit, String urlImageHome, String urlImageGuest) {
        matches = new PrevMatches(nameDivision, idTour, teamHome, goalHome, goalVisit, teamVisit);
        this.urlImageHome = urlImageHome;
        this.urlImageGuest = urlImageGuest;
    }

    public String getUrlImageHome() {
        return urlImageHome;
    }

    public String getUrlImageGuest() {
        return urlImageGuest;
    }

    public PrevMatches getMatches() {
        return matches;
    }

    @Override
    public String toString() {
        return "AllMatches{" + "nameDivision=" + matches.nameDivision + ", idTour=" + matches.idTour + ", teamHome=" + matches.teamHome +
                ", goalHome=" + matches.goalHome + ", goalVisit=" + matches.goalVisit + ", teamVisit=" + matches.teamVisit +
                " urlImageHome=" + this.urlImageHome + " urlImageGuest=" + this.urlImageGuest + "}\n";
    }
}
