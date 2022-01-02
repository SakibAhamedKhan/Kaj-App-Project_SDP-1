package com.example.kajappdemo;

public class StoringData {

    String name, username, email, phonenumber, password, nidnumber, accounttype, address, zipcode;

    public StoringData(String name, String username, String email, String phonenumber, String password, String nidnumber, String accounttype, String address, String zipcode) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.nidnumber = nidnumber;
        this.accounttype = accounttype;
        this.address = address;
        this.zipcode = zipcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNidnumber() {
        return nidnumber;
    }

    public void setNidnumber(String nidnumber) {
        this.nidnumber = nidnumber;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getZipcode() { return zipcode; }

    public void setZipCcode(String zipcode) { this.zipcode = zipcode; }
}
