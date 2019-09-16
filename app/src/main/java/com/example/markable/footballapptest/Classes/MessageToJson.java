package com.example.markable.footballapptest.Classes;

import java.util.ArrayList;

public class MessageToJson {

    public String messageLogic;
    private int id;
    private int tour;
    String date;
    private String team_name;
    private MessageRegister user_info;
    String responseFromServer;
    int settingForApp;
    ArrayList<Schedule> schedule;
    PrevMatches match;
    ArrayList<Player> players;
    int actionDB; //1 - insert; 2 - update
    public MessageToJson(String messageLogic, int id, String team_name, MessageRegister user_info) {
        this.messageLogic = messageLogic;
        this.id = id;
        this.team_name = team_name;
        this.user_info = user_info;

    }

    public MessageToJson(String messageLogic, PrevMatches match, ArrayList<Player> players){
        this.messageLogic = messageLogic;
        this.match = match;
        this.players = players;
    }

    public MessageToJson(String messageLogic, ArrayList<Schedule> schedule) {
        this.messageLogic = messageLogic;
        this.schedule = schedule;
    }

    public MessageToJson(String messageLogic, int id, int tour) {
        this.messageLogic = messageLogic;
        this.id = id;
        this.tour = tour;
    }

    public MessageToJson(String messageLogic, String team_name){
        this.messageLogic = messageLogic;
        this.team_name = team_name;
    }
    public MessageToJson(String mesageLogic, int id){
        this.messageLogic = mesageLogic;
        this.id = id;
    }

    public MessageToJson(String messageLogic, MessageRegister user_info){
        this.messageLogic = messageLogic;
        this.user_info = user_info;
    }


    public int getId() {
        return id;
    }

    public void setMessageLogic(String messageLogic) {
        this.messageLogic = messageLogic;
    }


    public String getResponseFromServer() {
        return responseFromServer;
    }

    public int getSettingForApp() {
        return settingForApp;
    }

    public void setActionDB(int actionDB) {
        this.actionDB = actionDB;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MessageToJson{" +
                "messageLogic='" + messageLogic + '\'' +
                ", id=" + id +
                ", team_name='" + team_name + '\'' +
                ", user_info=" + user_info +
                ", responseFromServer='" + responseFromServer + '\'' +
                ", settingForApp=" + settingForApp +
                '}';
    }
}
