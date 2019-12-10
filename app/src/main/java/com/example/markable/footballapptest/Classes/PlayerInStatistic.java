package com.example.markable.footballapptest.Classes;

public class PlayerInStatistic {

    public String typeCell;
    public Player player;
    public String amplua;

    public PlayerInStatistic(String typeCell, Player player) {
        this.typeCell = typeCell;
        this.player = player;
    }

    public PlayerInStatistic(String typeCell, String amplua) {
        this.typeCell = typeCell;
        this.amplua = amplua;
    }

    public PlayerInStatistic(String typeCell) {
        this.typeCell = typeCell;
    }

    @Override
    public String toString() {
        return "PlayerInStatistic{" +
                "typeCell='" + typeCell + '\'' +
                ", player=" + player +
                ", amplua='" + amplua + '\'' +
                '}' + "\n";
    }
}
