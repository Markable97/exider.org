package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;

public class Player {

    private int idPlayer;
    private String playerTeam;
    private String playerName;
    private String birhtday;
    private String amplua;
    private int number;
    private int games;
    private int goal;
    private int assist;
    private int yellowCard;
    private int redCard;
    Bitmap playerImage;

    public Player(int idPlayer,String playerTeam, String playerName, String amplua, String birhtday, int number,
                  int games, int goal, int assist, int yellowCard, int redCard) {
        this.idPlayer = idPlayer;
        this.playerTeam = playerTeam;
        this.playerName = playerName;
        this.birhtday = birhtday;
        this.amplua = amplua;
        this.number = number;
        this.games = games;
        this.goal = goal;
        this.assist = assist;
        this.yellowCard = yellowCard;
        this.redCard = redCard;
    }

    public Bitmap getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Bitmap playerImage) {
        this.playerImage = playerImage;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public String getPlayerTeam() {
        return playerTeam;
    }


    public String getPlayerName() {
        return playerName;
    }


    public String getBirhtday() {
        return birhtday;
    }

    public String getAmplua() {
        return amplua;
    }

    public int getNumber() {
        return number;
    }


    public int getGoal() {
        return goal;
    }

    public int getGames() {
        return games;
    }

    public int getAssist() {
        return assist;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public int getRedCard() {
        return redCard;
    }

    @Override
    public String toString() {
        return "Player{" +
                "idPlayer=" + idPlayer +
                ", playerTeam='" + playerTeam + '\'' +
                ", playerName='" + playerName + '\'' +
                ", birhtday='" + birhtday + '\'' +
                ", amplua='" + amplua + '\'' +
                ", number=" + number +
                ", games=" + games +
                ", goal=" + goal +
                ", assist=" + assist +
                ", yellowCard=" + yellowCard +
                ", redCard=" + redCard +
                '}'+"\n";
    }
}
