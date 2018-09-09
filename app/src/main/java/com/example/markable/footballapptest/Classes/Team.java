package com.example.markable.footballapptest.Classes;

public class Team {
    private int id;
    private String name;
    private String creation_date;
    private int id_division;

    public Team(int id, String name, String date, int id_division){
        setId(id);
        setName(name);
        setDate(date);
        setIdDivision(id_division);
    }

    void setId(int id){
        this.id = id;
    }
    void setName(String name){
        this.name = name;
    }
    void setDate(String date){
        this.creation_date = date;
    }
    void setIdDivision(int id){
        this.id_division = id;
    }

    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getDate(){
        return this.creation_date;
    }
    public int getIdDivision(){
        return this.id_division;
    }
}
