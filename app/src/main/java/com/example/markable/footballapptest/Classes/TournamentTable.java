package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;

import java.io.Serializable;

public class TournamentTable implements Serializable {
    private String divisionName;
    private String teamName;
    private int games;
    private int point;
    private int wins;
    private int draws;
    private int losses;
    private int goalsScored;
    private int goalsConceded;
    public transient Bitmap image;

    public TournamentTable(String division, String team, int games, int point, int wins, int draws, int losses,
                           int goalsScored, int goalsConceded){
        setDivision(division);
        setTeam(team);
        setPoint(point);
        setGames(games);
        setWins(wins);
        setDraws(draws);
        setLosses(losses);
        setGoalsScored(goalsScored);
        setGoalsConceded(goalsConceded);
    }

    @Override
    public String toString() {
        return getDivisionName() + " " + getTeamName() + " " + getGames() + " " + getWins() + " " + getDraws() + " " + getLosses()
                + " " +getGoalsScored() + " " + getGoalsConceded()+ " " + getPoint() + "\n";
    }

    private void setDivision(String division) {
        this.divisionName = division;
    }

    private void setTeam(String team) {
        this.teamName = team;
    }

    private void setGames(int games) {
        this.games = games;
    }
    private void setPoint(int point) {
        this.point = point;
    }
    private void setWins(int wins) {
        this.wins = wins;
    }
    private void setDraws(int draws) {
        this.draws = draws;
    }
    private void setLosses(int losses) {
        this.losses = losses;
    }
    private void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }
    private void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getGames() {
        return String.valueOf(games);
    }

    public String getPoint() {
        return String.valueOf(point);
    }

    public String getWins() {
        return String.valueOf(wins);
    }

    public String getDraws() {
        return String.valueOf(draws);
    }

    public String getLosses() {
        return String.valueOf(losses);
    }

    public String getGoalsScored() {
        return String.valueOf(goalsScored);
    }

    public String getGoalsConceded() {
        return String.valueOf(goalsConceded);
    }
}
