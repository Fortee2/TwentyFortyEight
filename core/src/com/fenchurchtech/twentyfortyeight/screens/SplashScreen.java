package com.fenchurchtech.twentyfortyeight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fenchurchtech.twentyfortyeight.MainGame;
import com.fenchurchtech.twentyfortyeight.logic.Splash;
import com.fenchurchtech.twentyfortyeight.logic.NumberBlock;

import java.util.List;

public class SplashScreen implements Screen, IUserAction {
    final MainGame _game;
    OrthographicCamera camera;
    List<NumberBlock> playBoard;
    StretchViewport viewport;
    String swipeDirecton ="";
    Splash gameBoard = new Splash();

    private static String[] gameDirections;
    private List<NumberBlock> cpyBoard;
    private final Vector3 touchPos;
    private final Sound collapsedSound =  Gdx.audio.newSound(Gdx.files.internal("click_005.ogg"));

    public SplashScreen(MainGame game) {
        _game = game;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(480,800,camera);

        camera.position.set(480/2, 800/2,0);
        viewport.apply();

        gameDirections = new String[]{"north", "south","east", "west", };

        touchPos = new Vector3(0, 0, 0);
    }

    @Override
    public void show() {

    }

    private void startGame() {
        GameScreen gSrc = new GameScreen(_game);
        _game.setScreen(gSrc);
        Gdx.input.setInputProcessor(new InputDecoder(gSrc));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        _game.batch.setProjectionMatrix(camera.combined);

        gameBoard.draw(playBoard, _game.batch, _game.bitmapFont);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
        gameBoard.dispose();
    }

    @Override
    public void swipeDirection(TouchInfo firstTouch, TouchInfo touchDelta) {

    }

    @Override
    public void touched(TouchInfo touchPoint) {
        startGame();
    }
}
