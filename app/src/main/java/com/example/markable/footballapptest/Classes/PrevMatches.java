package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;

import java.io.Serializable;

public class PrevMatches implements Serializable {
    int id_match;
    String nameDivision;
    int idTour;
    String teamHome;
    int goalHome;
    int goalVisit;
    String teamVisit;
    ImageFromServer imageHome;
    ImageFromServer imageVisit;

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

    public ImageFromServer getImageHome() {
        return imageHome;
    }

    public ImageFromServer getImageVisit() {
        return imageVisit;
    }

    public void setImageHome(ImageFromServer imageHome) {
        this.imageHome = imageHome;
    }

    public void setImageVisit(ImageFromServer imageVisit) {
        this.imageVisit = imageVisit;
    }

    @Override
    public String toString() {
        String result =id_match + " " + nameDivision + " " + idTour + " " + teamHome + " " + goalHome + " " + goalVisit + " " + teamVisit;
        return result;
    }
}
