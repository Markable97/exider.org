package com.example.markable.footballapptest.Classes;

import java.io.Serializable;

public class TournamentTable implements Serializable {
    private String divisionName;
    private String teamName;
    private int games;
    private int points;
    private int wins;
    private int draws;
    private int losses;
    private int goalScored;
    private int goalConceded;
    private int sc_con;
    private String imageBase64;
    private transient String urlImage;

    public TournamentTable(String division, String team, int games, int points, int wins, int draws, int losses,
                           int goalScored, int goalConceded){
        setDivision(division);
        setTeam(team);
        setPoint(points);
        setGames(games);
        setWins(wins);
        setDraws(draws);
        setLosses(losses);
        setGoalScored(goalScored);
        setGoalConceded(goalConceded);
    }

    @Override
    public String toString() {
        return getDivisionName() + " " + getTeamName() + " " + getGames() + " " + getWins() + " " + getDraws() + " " + getLosses()
                + " " +getGoalScored() + " " + getGoalConceded()+ " " + getPoint() + "\n";
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
        this.points = point;
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
    private void setGoalScored(int goalsScored) {
        this.goalScored = goalsScored;
    }
    private void setGoalConceded(int goalsConceded) {
        this.goalConceded = goalsConceded;
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
        return String.valueOf(points);
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

    public String getGoalScored() {
        return String.valueOf(goalScored);
    }

    public String getGoalConceded() {
        return String.valueOf(goalConceded);
    }

    public String getImageBase64() {
        return imageBase64;
    }
}
