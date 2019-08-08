package com.example.markable.footballapptest.Classes;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class Schedule {

    String time;
    Stadiums stadium;
    int checked;
    View view;

    public Schedule(String time, Stadiums stadium, int checked) {
        this.time = time;
        this.stadium = stadium;
        this.checked = checked;
    }

    public Schedule(String time, int checked) {
        this.time = time;
        this.checked = checked;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Stadiums getStadium() {
        return stadium;
    }

    public void setStadium(Stadiums stadium) {
        this.stadium = stadium;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "time='" + time + '\'' +
                ", stadium=" + stadium +
                ", checked=" + checked +
                "}\n";
    }

}
