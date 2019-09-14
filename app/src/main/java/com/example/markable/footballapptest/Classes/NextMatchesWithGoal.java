package com.example.markable.footballapptest.Classes;

import java.io.Serializable;

public class NextMatchesWithGoal implements Serializable {

    private int played;
    private int goalHome;
    private int goalGuest;

    public NextMatchesWithGoal() {
    }

    public int getPlayed() {
        return played;
    }

    public int getGoalHome() {
        return goalHome;
    }

    public int getGoalGuest() {
        return goalGuest;
    }

    @Override
    public String toString() {
        return "NextMatchesWithGoal{" +
                "played=" + played +
                ", goalHome=" + goalHome +
                ", goalGuest=" + goalGuest +
                '}';
    }
}
