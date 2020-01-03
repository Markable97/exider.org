package com.example.markable.footballapptest.Classes;

public class Division {

    public int idDivision;
    public String nameDivision;

    public Division(int idDivision, String nameDivision) {
        this.idDivision = idDivision;
        this.nameDivision = nameDivision;
    }

    @Override
    public String toString() {
        return "Division{" + "idDivision=" + idDivision + ", nameDivision=" + nameDivision + '}' + "\n";
    }

}
