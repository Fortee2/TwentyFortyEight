package com.fenchurchtech.twentyfortyeight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fenchurchtech.twentyfortyeight.MainGame;
import com.fenchurchtech.twentyfortyeight.logic.NumberBlock;

import java.util.List;

public class SettingsScreen implements Screen, IUserAction {
    BitmapFont font;
    MainGame _game;
    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch batch;

    public SettingsScreen(MainGame game){
        _game = game;
        font = new BitmapFont();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(480,800,camera);
        batch = _game.batch;
        camera.position.set(480/2, 800/2,0);
        viewport.apply();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        _game.batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch,"stuff", 10,100);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void swipeDirection(TouchInfo firstTouch, TouchInfo touchDelta) {

    }

    @Override
    public void touched(TouchInfo touchPoint) {

    }
}
