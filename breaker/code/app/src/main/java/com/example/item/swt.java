package com.example.item;

public class swt {
    String name;
    String i;
    String u;
    String t;
    int flag;

    public swt(String name, String i, String u, String t,int flag) {
        this.name = name;
        this.i = i;
        this.u = u;
        this.t = t;
        this.flag=flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
