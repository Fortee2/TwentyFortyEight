package com.fenchurchtech.twentyfortyeight.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

public class Splash {
    private final Sprite titleBlk ;
    private final Sprite tapBlk;
    private final Sprite whiteBlk;

    private final Sound collapsedSound =  Gdx.audio.newSound(Gdx.files.internal("click_005.ogg"));

    private TextureAtlas textureAtlas;

    private boolean playSound = true;
    private boolean gameOver = false;
    private int moveCount = 0;

    public Splash(){
        textureAtlas =  new TextureAtlas(Gdx.files.internal("sprites.txt"));
        titleBlk =   textureAtlas.createSprite("Title");
        tapBlk =   textureAtlas.createSprite("tap");
        whiteBlk =   textureAtlas.createSprite("white");
    }

    public  void draw(List<NumberBlock> blocks, SpriteBatch batch, BitmapFont bitmapFont){
        batch.begin();
        float x = 0.0f, y =600.0f;

       whiteBlk.setBounds(0,0,480,800);
       whiteBlk.draw(batch);

       titleBlk.setBounds(180,300,120,200);
       titleBlk.draw(batch);

       tapBlk.setBounds(190,100,75,100);
       tapBlk.draw(batch);

        batch.end();
    }

    public void dispose(){
        collapsedSound.dispose();
        textureAtlas.dispose();
    }
}
