package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;

public class Player implements Serializable/*,Parcelable*/{

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
                "image= " + playerImage +
                '}'+"\n";
    }

   /* protected Player(Parcel in) {
        idPlayer = in.readInt();
        playerTeam = in.readString();
        playerName = in.readString();
        birhtday = in.readString();
        amplua = in.readString();
        number = in.readInt();
        games = in.readInt();
        goal = in.readInt();
        assist = in.readInt();
        yellowCard = in.readInt();
        redCard = in.readInt();
        playerImage = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPlayer);
        dest.writeString(playerTeam);
        dest.writeString(playerName);
        dest.writeString(birhtday);
        dest.writeString(amplua);
        dest.writeInt(number);
        dest.writeInt(games);
        dest.writeInt(goal);
        dest.writeInt(assist);
        dest.writeInt(yellowCard);
        dest.writeInt(redCard);
        dest.writeValue(playerImage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };*/
/**
 *Если этот метод возвращает отрицательное число, то текущий объект будет располагаться перед тем,
 * который передается через параметр. Если метод вернет положительное число, то, наоборот, после второго объекта.
 * Если метод возвратит ноль, значит, оба объекта равны.
 * **/
    public static Comparator<Player> sortGoal = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
            return o2.getGoal() - o1.getGoal();
        }
    };
    public static Comparator<Player> sortAssist = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
            return o2.getAssist() - o1.getAssist();
        }
    };
}
