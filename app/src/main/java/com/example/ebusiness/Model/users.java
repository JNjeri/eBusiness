package com.example.ebusiness.Model;

public class users
{
    private  String Name,Phone,Password;

    public users()

    {

    }

    public users(String name, String phone, String password) {
        Name = name;
        Phone = phone;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
