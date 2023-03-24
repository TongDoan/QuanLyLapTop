package com.example.bttl;

public class chitietspdamua {
    String hinhanh;
    String tensp;
    String giasp;
    int soluong;

    public chitietspdamua(String hinhanh, String tensp, String giasp, int soluong) {
        this.hinhanh = hinhanh;
        this.tensp = tensp;
        this.giasp = giasp;
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
