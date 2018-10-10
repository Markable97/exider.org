package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;

public class Bombardier {

    private String playetName;
    Bitmap playerImage;
    private int countGoals;

    public Bombardier(String playetName, int countGoals) {
        this.playetName = playetName;
        this.countGoals = countGoals;
    }

    public void setPlayerImage(Bitmap playerImage) {
        this.playerImage = playerImage;
    }

    public String getPlayetName() {
        return playetName;
    }

    public Bitmap getPlayerImage() {
        return playerImage;
    }

    public int getCountGoals() {
        return countGoals;
    }
}
