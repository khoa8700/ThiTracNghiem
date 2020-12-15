package com.example.thitracnghiem.model;

public class HighScore {
    double diem;
    String id;
    public HighScore(){}

    public HighScore(double diem,String id) {
        this.id=id;
        this.diem = diem;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
