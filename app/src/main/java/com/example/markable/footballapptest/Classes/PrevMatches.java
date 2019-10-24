package com.example.markable.footballapptest.Classes;

import java.io.Serializable;

public class PrevMatches implements Serializable {
    int id_match;
    String nameDivision;
    int idTour;
    String teamHome;
    int goalHome;
    int goalVisit;
    String teamVisit;
    String imageHome;
    String imageGuest;
    ImageFromServer imageHomeImage;
    ImageFromServer imageVisitImage;

    public PrevMatches(int id_match, int goalHome, int goalVisit) {
        this.id_match = id_match;
        this.goalHome = goalHome;
        this.goalVisit = goalVisit;
    }


    public PrevMatches(int idMatch, String nameDivision, int idTour, String teamHome, int goalHome, int goalVisit, String teamVisit) {
        this.id_match = idMatch;
        this.nameDivision = nameDivision;
        this.idTour = idTour;
        this.teamHome = teamHome;
        this.goalHome = goalHome;
        this.goalVisit = goalVisit;
        this.teamVisit = teamVisit;
    }

    public PrevMatches(int idTour, String nameHome, String teamVisit){
        this.idTour = idTour;
        this.teamHome = nameHome;
        this.teamVisit = teamVisit;
    }

    public int getIdMatch() {
        return id_match;
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

    public ImageFromServer getImageHomeImage() {
        return imageHomeImage;
    }

    public ImageFromServer getImageVisitImage() {
        return imageVisitImage;
    }

    public void setImageHomeImage(ImageFromServer imageHomeImage) {
        this.imageHomeImage = imageHomeImage;
    }

    public void setImageVisitImage(ImageFromServer imageVisitImage) {
        this.imageVisitImage = imageVisitImage;
    }

    public String getImageHome() {
        return imageHome;
    }

    public String getImageGuest() {
        return imageGuest;
    }

    @Override
    public String toString() {
        String result =id_match + " " + nameDivision + " " + idTour + " " + teamHome + " " + goalHome + " " + goalVisit + " " + teamVisit;
        return result;
    }
}
