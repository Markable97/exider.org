package com.example.markable.footballapptest.Classes;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerView {

    String teamName;
    CheckBox inGame;
    EditText number;
    TextView playerName;
    EditText goal;
    EditText assist;
    EditText yellowCard;
    EditText redCard;

    public PlayerView(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getInGame() {
        if(inGame.isChecked() == true){
            return 1;
        }else{
            return 0;
        }
    }

    public int getNumber() {
        if(number.getText().length() > 0){
            return Integer.parseInt(String.valueOf(number.getText()));
        }
        else{
            return 0;
        }
    }

    public int getGoal() {
        if(goal.getText().length() > 0){
            return Integer.parseInt(String.valueOf(goal.getText()));
        }else{
            return 0;
        }
    }

    public int getAssist() {
        if(assist.getText().length() > 0){
            return Integer.parseInt(String.valueOf(assist.getText()));
        }else{
            return 0;
        }
    }

    public int getYellowCard() {
        if(yellowCard.getText().length() > 0){
            return Integer.parseInt(String.valueOf(yellowCard.getText()));
        }else{
            return 0;
        }
    }

    public int getRedCard() {

        if(redCard.getText().length() > 0){
            return Integer.parseInt(String.valueOf(redCard.getText()));
        }else{
            return 0;
        }
    }

    public void setInGame(CheckBox inGame) {
        this.inGame = inGame;
    }

    public void setNumber(EditText number) {
        this.number = number;
    }

    public void setPlayerName(TextView playerName) {
        this.playerName = playerName;
    }

    public void setGoal(EditText goal) {
        this.goal = goal;
    }

    public void setAssist(EditText assist) {
        this.assist = assist;
    }

    public void setYellowCard(EditText yellowCard) {
        this.yellowCard = yellowCard;
    }

    public void setRedCard(EditText redCard) {
        this.redCard = redCard;
    }


    @Override
    public String toString() {

        String game;
        if(inGame.isChecked()){
            game = "В игре";
        }else{
            game = "Нет в игре";
        }
        String sNumber = null;
        if(number.getText().length() > 0){
            sNumber = String.valueOf(number.getText());
        }
        String sName = null;
        if(playerName.getText().length() > 0){
            sName = String.valueOf(playerName.getText());
        }
        String sGoal = null;
        if(goal.getText().length() > 0){
            sGoal = String.valueOf(goal.getText());
        }
        String sAssist = null;
        if(assist.getText().length() > 0){
            sAssist = String.valueOf(assist.getText());
        }
        String sYellow = null;
        if(yellowCard.getText().length() > 0){
            sYellow = String.valueOf(yellowCard.getText());
        }
        String sRed = null;
        if(redCard.getText().length() > 0){
            sRed = String.valueOf(redCard.getText());
        }
        return "PlayerView{" +
                "teamName='" + teamName + '\'' +
                ", inGame=" + game +
                ", number=" + sNumber +
                ", playerName=" + sName +
                ", goal=" + sGoal +
                ", assist=" + sAssist +
                ", yellowCard=" + sYellow +
                ", redCard=" + sRed +
                '}';
    }
}
