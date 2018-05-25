package com.hfad.myresidentadmin.Model;

/**
 * Created by icefrog on 5/25/18.
 */

public class SOS {


    private String User;
    private String Unit;
    private String Status;
    private String Date;
    private String Time;



    public SOS() {
    }


    public SOS(String user, String unit, String Status, String date, String time) {
        this.User = user;
        this.Unit = unit;
        this.Status = Status; //Default is 0, 0: Pending, 1: On My Way, 2:Settle
        this.Date = date;
        this.Time = time;

    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
