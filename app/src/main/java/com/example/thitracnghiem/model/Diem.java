package com.example.thitracnghiem.model;

public class Diem {
    Double diem;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Diem(double diem, String id) {
        this.diem=diem;
        this.id=id;
    }
    public Double getDiem() {
        return diem;
    }

    public void setDiem(Double diem) {
        this.diem = diem;
    }

    public Diem(Double diem) {
        this.diem = diem;
    }
}
