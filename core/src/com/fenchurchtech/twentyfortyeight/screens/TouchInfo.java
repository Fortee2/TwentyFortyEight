package com.fenchurchtech.twentyfortyeight.screens;

public class TouchInfo {
    public float touchX = 0;
    public float touchY = 0;
    public boolean touched = false;

    public TouchInfo(){

    }

    public TouchInfo(int x, int y){
        touchX = x;
        touchY = y;
    }

    public TouchInfo sub (TouchInfo ti){
        TouchInfo rtn = new TouchInfo();

        rtn.touchX = touchX - ti.touchX;
        rtn.touchY = touchY - ti.touchY;
        rtn.touched = false;

        return rtn;
    }

    public boolean equals(TouchInfo ti){
        return ti.touchY == this.touchY && ti.touchX == this.touchX;
    }
}