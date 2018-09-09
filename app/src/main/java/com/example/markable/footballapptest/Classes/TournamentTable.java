package com.example.markable.footballapptest.Classes;

public class TournamentTable {
    private String divisionName;
    private String teamName;
    private int games;
    private int point;
    private int wins;
    private int draws;
    private int losses;

    public TournamentTable(String division, String team, int games, int point, int wins, int draws, int losses){
        setDivision(division);
        setTeam(team);
        setPoint(point);
        setGames(games);
        setWins(wins);
        setDraws(draws);
        setLosses(losses);
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
    public String getDivisionName() {
        return divisionName;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getGames() {
        return games;
    }

    public int getPoint() {
        return point;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }
}
