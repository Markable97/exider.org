package com.example.markable.footballapptest.Classes;

public class MessageToJson {

    public String messageLogic;
    private int id;
    private String team_name;
    private MessageRegister user_info;
    String responseFromServer;
    int settingForApp;

    public MessageToJson(String messageLogic, int id, String team_name, MessageRegister user_info) {
        this.messageLogic = messageLogic;
        this.id = id;
        this.team_name = team_name;
        this.user_info = user_info;

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

    public String getMessageLogic() {
        return messageLogic;
    }

    public int getId() {
        return id;
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
                ", id=" + id +
                ", team_name='" + team_name + '\'' +
                ", user_info=" + user_info +
                ", responseFromServer='" + responseFromServer + '\'' +
                ", settingForApp=" + settingForApp +
                '}';
    }
}
