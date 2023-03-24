package com.example.bttl;

public class user {
   private int id;
   private String email,matkhat,hinhanh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhat() {
        return matkhat;
    }

    public void setMatkhat(String matkhat) {
        this.matkhat = matkhat;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public user(int id, String email, String matkhat, String hinhanh) {
        this.id = id;
        this.email = email;
        this.matkhat = matkhat;
        this.hinhanh = hinhanh;
    }
}
