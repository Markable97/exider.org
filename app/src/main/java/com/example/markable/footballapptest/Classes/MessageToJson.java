package com.example.markable.footballapptest.Classes;

public class MessageToJson {

    String messageLogic;
    private int id_division;
    private String id_team;
    private MessageRegister user_info;


    public MessageToJson(String messageLogic, int id_division, String id_team, MessageRegister user_info) {
        this.messageLogic = messageLogic;
        this.id_division = id_division;
        this.id_team = id_team;
        this.user_info = user_info;

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

    public String getId_team() {
        return id_team;
    }

    public MessageRegister getUser_info() {
        return user_info;
    }


    @Override
    public String toString() {
        return "MessageToJson{" + "messageLogic=" + messageLogic + ", id_division=" + id_division + ", id_team=" + id_team + ", user_info=" + user_info + '}';
    }

}
