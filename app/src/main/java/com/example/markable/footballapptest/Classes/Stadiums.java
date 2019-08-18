package com.example.markable.footballapptest.Classes;

public class Stadiums {

    int idStadium;
    String nameStadium;

    public Stadiums(int idStadium, String nameStadium) {
        this.idStadium = idStadium;
        this.nameStadium = nameStadium;
    }

    public int getIdStadium() {
        return idStadium;
    }

    public void setIdStadium(int idStadium) {
        this.idStadium = idStadium;
    }

    public String getNameStadium() {
        return nameStadium;
    }

    public void setNameStadium(String nameStadium) {
        this.nameStadium = nameStadium;
    }

    @Override
    public String toString() {
        return "Stadiums{" +
                "idStadium=" + idStadium +
                ", nameStadium='" + nameStadium + '\'' +
                "}\n";
    }
}
