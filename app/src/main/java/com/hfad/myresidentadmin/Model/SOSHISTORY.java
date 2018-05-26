package com.hfad.myresidentadmin.Model;

/**
 * Created by icefrog on 5/26/18.
 */

public class SOSHISTORY {

    private String User;
    private String Unit;
    private String RequestDateTime;
    private String DisableDateTime;

    public SOSHISTORY() {
    }

    public SOSHISTORY(String user, String unit, String requestDateTime, String disableDateTime) {
        this.User = user;
        this.Unit = unit;
        this.RequestDateTime = requestDateTime;
        this.DisableDateTime = disableDateTime;

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

    public String getRequestDateTime() {
        return RequestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        RequestDateTime = requestDateTime;
    }

    public String getDisableDateTime() {
        return DisableDateTime;
    }

    public void setDisableDateTime(String disableDateTime) {
        DisableDateTime = disableDateTime;
    }
}
