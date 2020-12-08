package com.example.thitracnghiem.model;

public class Question {
    private String Q;
    private String A;
    public Question(){}

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public Question(String q, String a) {
        Q = q;
        A = a;
    }
}
