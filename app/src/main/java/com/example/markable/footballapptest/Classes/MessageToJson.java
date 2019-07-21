package com.example.markable.footballapptest.Classes;

public class MessageToJson {

    public String messageLogic;
    private int id_division;
    private String team_name;
    private MessageRegister user_info;
    String responseFromServer;
    int settingForApp;

    public MessageToJson(String messageLogic, int id_division, String team_name, MessageRegister user_info) {
        this.messageLogic = messageLogic;
        this.id_division = id_division;
        this.team_name = team_name;
        this.user_info = user_info;

    }

    public MessageToJson(String messageLogic, String team_name){
        this.messageLogic = messageLogic;
        this.team_name = team_name;
    }
    public MessageToJson(String mesageLogic, int id_division){
        this.messageLogic = mesageLogic;
        this.id_division = id_division;
    }

    public MessageToJson(String messageLogic, MessageRegister user_info){
        this.messageLogic = messageLogic;
        this.user_info = user_info;
    }

    public String getMessageLogic() {
        return messageLogic;
    }

    public int getId_division() {
        return id_division;
    }

    public String getTeam_name() {
        return team_name;
    }

    public MessageRegister getUser_info() {
        return user_info;
    }

    public String getResponseFromServer() {
        return responseFromServer;
    }

    public int getSettingForApp() {
        return settingForApp;
    }

    @Override
    public String toString() {
        return "MessageToJson{" +
                "messageLogic='" + messageLogic + '\'' +
                ", id_division=" + id_division +
                ", team_name='" + team_name + '\'' +
                ", user_info=" + user_info +
                ", responseFromServer='" + responseFromServer + '\'' +
                ", settingForApp=" + settingForApp +
                '}';
    }
}
