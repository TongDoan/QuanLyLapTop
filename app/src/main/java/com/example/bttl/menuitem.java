package com.example.bttl;

public class menuitem {
    int image;
    String tenitem;

    public menuitem(int image, String tenitem) {
        this.image = image;
        this.tenitem = tenitem;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTenitem() {
        return tenitem;
    }

    public void setTenitem(String tenitem) {
        this.tenitem = tenitem;
    }
}
