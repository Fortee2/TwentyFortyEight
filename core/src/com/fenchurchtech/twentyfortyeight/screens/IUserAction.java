package com.fenchurchtech.twentyfortyeight.screens;

public interface IUserAction {

     void swipeDirection(TouchInfo firstTouch, TouchInfo touchDelta);

     void touched(TouchInfo touchPoint);

}
