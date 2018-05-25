package com.hfad.myresidentadmin.Model;

/**
 * Created by icefrog on 2/19/18.
 */

public class User {
    private String Name,Password,Phone,IsStaff,Age,Level,No,Type,Unit,carPlateNo,Email,Gender,Ic,secureCode,Outstanding;

    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public String getAge() {
        return Age;
    }

    public String getOutstanding() {
        return Outstanding;
    }

    public void setOutstanding(String outstanding) {
        Outstanding = outstanding;
    }

    public void setAge(String age) {
        Age = age;

    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getCarPlateNo() {
        return carPlateNo;
    }

    public void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getIc() {
        return Ic;
    }

    public void setIc(String ic) {
        Ic = ic;
    }

    public String getSecureCode() {
        return secureCode;
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }

    public User() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }
}
