package com.example.markable.footballapptest.Classes;

public class MessageRegister {
    String name;
    String email;
    String team;
    String password;

    public MessageRegister(String name, String email, String team, String password) {
        this.name = name;
        this.email = email;
        this.team = team;
        this.password = password;
    }

    public MessageRegister(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "MessageRegister{" + "name=" + name + ", email=" + email + ", team=" + team + ", password=" + password + '}';
    }
}
