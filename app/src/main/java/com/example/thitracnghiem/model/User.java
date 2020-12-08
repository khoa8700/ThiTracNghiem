package com.example.thitracnghiem.model;

public class User {
    private String name;
    private int age;
    private String school;
    private String classs;
    public User(){}

    public User(String name, int age, String school, String classs) {
        this.name = name;
        this.age = age;
        this.school = school;
        this.classs = classs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }
}
