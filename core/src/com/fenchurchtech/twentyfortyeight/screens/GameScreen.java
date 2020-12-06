package com.fenchurchtech.twentyfortyeight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fenchurchtech.twentyfortyeight.MainGame;
import com.fenchurchtech.twentyfortyeight.logic.GameBoard;
import com.fenchurchtech.twentyfortyeight.logic.NumberBlock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameScreen implements Screen, IUserAction {
    final MainGame _game;
    OrthographicCamera camera;
    List<NumberBlock> playBoard;
    Viewport viewport;
    String swipeDirecton ="";
    GameBoard gameBoard = new GameBoard();

    private static String[] gameDirections;
    private List<NumberBlock> cpyBoard;
    private final Vector3 touchPos;
    private final Sound collapsedSound =  Gdx.audio.newSound(Gdx.files.internal("click_005.ogg"));

    public GameScreen(MainGame game) {
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
        startGame();
    }

    private void startGame() {
        playBoard = gameBoard.initializeBlocks(4);
        cpyBoard = GameBoard.copyGameBoard(playBoard);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        _game.batch.setProjectionMatrix(camera.combined);

        gameBoard.draw(playBoard, _game.batch);

        if(!swipeDirecton.isEmpty()){
            //returns false when all moves have been made
            if(!gameBoard.executeShift(swipeDirecton,playBoard)) {
                swipeDirecton = "";
                resetBoardState(playBoard);
                if(gameBoard.didGameBoardChange(cpyBoard, playBoard)) {
                    if(gameBoard.isPlaySound()){
                        collapsedSound.play();
                    }
                    GameBoard.setNumberBlock(playBoard);
                }
            }
        }else{
            cpyBoard = GameBoard.copyGameBoard(playBoard);
        }

        //code here to transition to end game screen
       if(isGameOver(playBoard)){
           gameBoard.setGameOver( true);
       }
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
        TouchInfo diffTouch = firstTouch.sub(touchDelta);  //used to figure out which axis had the most change

        if(Math.abs(diffTouch.touchX) >  Math.abs( diffTouch.touchY)){
            if(touchDelta.touchX < firstTouch.touchX){
                swipeDirecton = "west";
            }
            if(touchDelta.touchX > firstTouch.touchX){
                swipeDirecton = "east";
            }
        }else {
            if(touchDelta.touchY < firstTouch.touchY){
                swipeDirecton = "north";
            }
            if(touchDelta.touchY > firstTouch.touchY){
                swipeDirecton = "south";
            }
        }
    }

    @Override
    public void touched(TouchInfo touchPoint) {
        touchPos.x = touchPoint.touchX;
        touchPos.y = touchPoint.touchY;
        camera.unproject(touchPos);

        if(gameBoard.isGameOver()){
            if(touchPos.x <= 345
                && touchPos.x >=115
                && touchPos.y <= 380
                && touchPos.y >= 280){
                gameBoard.setGameOver(false);
                startGame();
            }
        }else {
            if (touchPos.x <= 440
                    && touchPos.x >= 390
                    && touchPos.y <= 780
                    && touchPos.y >= 730) {

                gameBoard.setPlaySound(!gameBoard.isPlaySound());
                //dispose();

            }
        }
    }

    private void resetBoardState(List<NumberBlock> gameBoard){
        for (NumberBlock nb:gameBoard
             ) {
            nb.set_state("u");
        }
    }

    private  boolean isGameOver(List<NumberBlock> playBoard){
        if(TwentyFortyEightExists(playBoard)){
            return true;
        }

        if( allSquaresFull(playBoard)){
            //Check to see if there are any moves left
            for (String key : gameDirections) {
                List<NumberBlock> northList = GameBoard.copyGameBoard(playBoard);
                gameBoard.executeShift(key,northList);
                if(gameBoard.didGameBoardChange(playBoard,northList))
                    return false;
            }
            return true;
        }

        return false;
    }

    private static boolean TwentyFortyEightExists(List<NumberBlock> board){
       return GameBoard.findBlock(board, 2048) != null;
    }

    private static boolean allSquaresFull(List<NumberBlock> board){
        return GameBoard.findBlock(board, 0) == null;
    }
}
