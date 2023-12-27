package com.example.doanqlsinhvien.Model;

public class registerSubject {
    int id;
    String name, nameClass, timeStart, timeEnd;
    boolean rbCheck;

    public registerSubject(int id, String name, String nameClass, String timeStart, String timeEnd, boolean rbCheck) {
        this.id = id;
        this.name = name;
        this.nameClass = nameClass;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.rbCheck = rbCheck;
    }

    public boolean isRbCheck() {
        return rbCheck;
    }

    public void setRbCheck(boolean rbCheck) {
        this.rbCheck = rbCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }
}
