package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;

public class Player implements Serializable, Parcelable{

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
    private int penalty;
    private int penalty_out;
    private int own_goal;
    private int inGame;
    private transient PlayerView playerView;

    public Player(int idPlayer, String playerTeam, String playerName, String birhtday, String amplua, int number,
                  int games, int goal, int assist, int yellowCard, int redCard, int penalty, int penalty_out,
                  int own_goal) {
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
        this.penalty = penalty;
        this.penalty_out = penalty_out;
        this.own_goal = own_goal;
    }

    public Player(int idPlayer, String playerTeam, String playerName, String amplua, String birhtday, int number,
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

    public Player(String playerTeam, String playerName) {
        this.playerTeam = playerTeam;
        this.playerName = playerName;
    }

    public Bitmap getPlayerImage() {
        return playerImage;
    }


    public int getIdPlayer() {
        return idPlayer;
    }

    public String getPlayerTeam() {
        return playerTeam;
    }


    public String getPlayerName() {

        String[] str = playerName.split(" ");


        return str[0] + " " + str[1];
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

    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
        if(playerView.inGame.isChecked() == true){
            this.games = 1;
        }
        if(playerView.number.getText().length() > 0){
            this.number = Integer.parseInt(String.valueOf(playerView.number.getText()));
        }
        /*if(playerView.goal.getText().length() > 0){
            this.goal = Integer.parseInt(String.valueOf(playerView.goal.getText()));
        }*/
        if(playerView.assist.getText().length() > 0){
            this.assist = Integer.parseInt(String.valueOf(playerView.assist.getText()));
        }
        if(playerView.yellowCard.getText().length() > 0){
            this.yellowCard = Integer.parseInt(String.valueOf(playerView.yellowCard.getText()));
        }
        if(playerView.redCard.getText().length() > 0){
            this.redCard = Integer.parseInt(String.valueOf(playerView.redCard.getText()));
        }
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

    public int getPenalty() {
        return penalty;
    }

    public int getPenalty_out() {
        return penalty_out;
    }

    public int getOwn_goal() {
        return own_goal;
    }

    public int getInGame() {
        return inGame;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public void setPenalty_out(int penalty_out) {
        this.penalty_out = penalty_out;
    }

    public void setOwn_goal(int own_goal) {
        this.own_goal = own_goal;
    }

    public PlayerView getPlayerView() {
        return playerView;
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
                ", inGame=" + inGame +
                ", goal=" + goal +
                ", assist=" + assist +
                ", yellowCard=" + yellowCard +
                ", redCard=" + redCard +
                "image= " + playerImage +
                '}'+"\n";
    }

   protected Player(Parcel in) {
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
    };
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
