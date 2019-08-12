package com.example.markable.footballapptest.Classes;

import android.view.View;

public class Schedule {

    String match_date;
    String match_time;
    int id_stadium;
    int id_tour;
    String name_stadium;
    int id_match;
    int id_division;
    String name_division;
    String team_home;
    String team_guest;
    int busy_time;

    //transient Stadiums stadium;
    transient View view;

    public Schedule(String match_date, String match_time, int id_stadium, int id_tour, String name_stadium,
                    int id_match, String team_home, String team_guest, int busy_time) {
        this.match_date = match_date;
        this.match_time = match_time;
        this.id_stadium = id_stadium;
        this.id_tour = id_tour;
        this.name_stadium = name_stadium;
        this.id_match = id_match;
        this.team_home = team_home;
        this.team_guest = team_guest;
        this.busy_time = busy_time;
    }

    /*public Schedule(String match_time, Stadiums stadium, int busy_time) {
        this.match_time = match_time;
        this.stadium = stadium;
        this.busy_time = busy_time;
    }*/

    public Schedule(String match_time, int busy_time) {
        this.match_time = match_time;
        this.busy_time = busy_time;
    }

    public Schedule(String match_date,String match_time, int id_stadium, int id_match, int busy_time) {
        this.match_date = match_date;
        this.match_time = match_time;
        this.id_stadium = id_stadium;
        this.id_match = id_match;
        this.busy_time = busy_time;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    /*public Stadiums getStadium() {
        return stadium;
    }*/

    /*public void setStadium(Stadiums stadium) {
        this.stadium = stadium;
    }*/

    public String getMatch_date() {
        return match_date;
    }


    public int getId_stadium() {
        return id_stadium;
    }


    public String getName_stadium() {
        return name_stadium;
    }


    public int getId_match() {
        return id_match;
    }


    public String getTeam_home() {
        return team_home;
    }


    public String getTeam_guest() {
        return team_guest;
    }


    public int getBusy_time() {
        return busy_time;
    }

    public String getName_division() {
        return name_division;
    }

    public int getId_tour() {
        return id_tour;
    }

    public int getId_division() {
        return id_division;
    }

    public void setId_division(int id_division) {
        this.id_division = id_division;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public void setId_stadium(int id_stadium) {
        this.id_stadium = id_stadium;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }

    public void setName_stadium(String name_stadium) {
        this.name_stadium = name_stadium;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public void setName_division(String name_division) {
        this.name_division = name_division;
    }

    public void setTeam_home(String team_home) {
        this.team_home = team_home;
    }

    public void setTeam_guest(String team_guest) {
        this.team_guest = team_guest;
    }

    public void setBusy_time(int busy_time) {
        this.busy_time = busy_time;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "match_date='" + match_date + '\'' +
                ", match_time='" + match_time + '\'' +
                ", id_stadium=" + id_stadium +
                ", id_tour=" + id_tour +
                ", name_stadium='" + name_stadium + '\'' +
                ", id_match=" + id_match +
                ", name_division='" + name_division + '\'' +
                ", team_home='" + team_home + '\'' +
                ", team_guest='" + team_guest + '\'' +
                ", busy_time=" + busy_time +
                ", view=" + view +
                '}'+"\n";
    }
}
