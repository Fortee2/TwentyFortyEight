package com.fenchurchtech.twentyfortyeight.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class NumberBlock {
    private int _column;
    private int _row;
    private int _value;
    private String _state;
    private Texture _texture;

    public NumberBlock(int column, int row, int value, String state){
        _column = column;
        _row =row;
        _value = value;
        _state = state;
    }

    public int get_column() {
        return _column;
    }

    public void set_column(int _column) {
        this._column = _column;
    }

    public int get_row() {
        return _row;
    }

    public void set_row(int _row) {
        this._row = _row;
    }

    public int get_value() {
        return _value;
    }

    public void set_value(int _value) {
        this._value = _value;
    }

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }

    public  Texture get_texture(){
        String fileName = "";

        if(_value == 0 ){
            fileName ="blank";
        }else{
            fileName = String.valueOf(_value);
        }

        return new  Texture(Gdx.files.internal(fileName + ".png"));
    }
}
