package com.fenchurchtech.twentyfortyeight.screens;

import com.badlogic.gdx.InputProcessor;

public class InputDecoder implements InputProcessor {
    private TouchInfo ti = new TouchInfo();
    private TouchInfo draggedTouch = new TouchInfo();
    private IUserAction userAction;

    public InputDecoder(IUserAction userAction) {
        this.userAction = userAction;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        ti.touchX = screenX;
        ti.touchY = screenY;
        ti.touched = true;

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        TouchInfo subTi = new TouchInfo(screenX, screenY);

        if(userAction != null){
            if(ti.equals(subTi)){
                userAction.touched(subTi);
            }else {
                userAction.swipeDirection(ti, subTi);
            }
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
