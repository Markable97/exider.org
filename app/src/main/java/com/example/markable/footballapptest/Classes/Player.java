package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;

public class Player {

    private String playerName;
    transient Bitmap playerImage;
    private int games;
    private int goal;
    private int assist;
    private int yellowCard;
    private int redCard;

    public Player(String playerName, int games, int goal, int assist, int yellowCard, int redCard) {
        this.playerName = playerName;
        this.games = games;
        this.goal = goal;
        this.assist = assist;
        this.yellowCard = yellowCard;
        this.redCard = redCard;
    }

    public void setPlayerImage(Bitmap playerImage) {
        this.playerImage = playerImage;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Bitmap getPlayerImage() {
        return playerImage;
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
}
