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

public class GameBoard {
    private final Sprite bkgrd;
    private final Sprite titleBlk ;
    private final Sprite nb0Blk ;
    private final Sprite overlay;
    private final Sprite retryBlk;
    private final Sprite volumeBlk;
    private final Sprite settingsBlk;
    private final Sprite muteBlk;
    private final Sprite gameOverBlk;
    private final Sprite nb2Blk;
    private final Sprite nb4Blk;
    private final Sprite nb8Blk;
    private final Sprite nb16Blk;
    private final Sprite nb32Blk;
    private final Sprite nb64Blk;
    private final Sprite nb128Blk;
    private final Sprite nb256Blk;
    private final Sprite nb512Blk;
    private final Sprite nb1024Blk;
    private final Sprite nb2048lk;
    private final Sprite whiteBlk;

    private final Sound collapsedSound =  Gdx.audio.newSound(Gdx.files.internal("click_005.ogg"));

    public TextureAtlas textureAtlas;

    private boolean playSound = true;
    private boolean gameOver = false;
    private int moveCount = 0;

    public  GameBoard(){
        textureAtlas =  new TextureAtlas(Gdx.files.internal("sprites.txt"));

        bkgrd = textureAtlas.createSprite("background");

         nb4Blk =  textureAtlas.createSprite("4");
         nb8Blk =  textureAtlas.createSprite("8");
         nb16Blk =  textureAtlas.createSprite("16");
         nb32Blk =  textureAtlas.createSprite("32");
         nb64Blk =  textureAtlas.createSprite("64");
         nb128Blk =  textureAtlas.createSprite("128");
         nb256Blk =  textureAtlas.createSprite("256");
         nb512Blk =  textureAtlas.createSprite("512");
         nb1024Blk =  textureAtlas.createSprite("1024");
         nb2048lk =  textureAtlas.createSprite("2048");
         whiteBlk =  textureAtlas.createSprite("white");

         titleBlk =   textureAtlas.createSprite("Title");
         nb0Blk =  textureAtlas.createSprite("wood_blank");
         gameOverBlk  =  textureAtlas.createSprite("Gameover");
         overlay =  textureAtlas.createSprite("overlay");
         retryBlk =  textureAtlas.createSprite("retry");
         volumeBlk =  textureAtlas.createSprite("volume");
         settingsBlk =  textureAtlas.createSprite("settings");
         muteBlk =  textureAtlas.createSprite("mute");
         nb2Blk =   textureAtlas.createSprite("wood_blocks");
    }

    public  int getMoveCount(){
        return moveCount;
    }

    public void incrementMoveCount(){
        moveCount++;
    }

    public void resetMoveCount(){
        moveCount = 0;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public List<NumberBlock> initializeBlocks(int maxRowsCols){
        List<NumberBlock> gameBoard = new ArrayList<>();

        for (int i = 1; i < maxRowsCols +1 ; i++) {
            for (int j = 1; j <  maxRowsCols +1; j++) {
                NumberBlock blk;

                blk = new NumberBlock(j, i, 0, "u");
                gameBoard.add(blk);
            }
        }

        setNumberBlock(gameBoard);
        setNumberBlock(gameBoard);

        return gameBoard;
    }

    public  void draw(List<NumberBlock> blocks, SpriteBatch batch, BitmapFont bitmapFont){
        batch.begin();
        float x = 0.0f, y =600.0f;

        batch.draw(bkgrd, 0,0,480,800);
        batch.draw(titleBlk, 10,675,75,75);

/*        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(2);
        bitmapFont.draw(batch,"Time:  00:01", 240, 730,95,10,false );
        bitmapFont.draw(batch,"Moves:  1", 390, 625,95,10,false );*/

        if(gameOver){
            drawGameOver(batch, bitmapFont);
        }else{
            drawToolbar(batch);
            drawNumberBlocks(blocks, batch, x, y);
        }

        batch.end();
    }

    private void drawNumberBlocks(List<NumberBlock> blocks, SpriteBatch batch, float x, float y) {
        for (NumberBlock nb: blocks) {
            if(nb.get_column() == 1){
                x = 50;
                y= y - 95;
            }

            batch.draw(retrieveSprite(nb.get_value()), x, y,95,95);
            x = x + 95;
        }
    }

    private void drawGameOver(SpriteBatch batch, BitmapFont bitmapFont) {
        batch.draw(overlay, 0, 0, 480, 800);
        batch.draw(gameOverBlk, 10,400,460,60 );
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(2);
        bitmapFont.draw(batch, String.format("Moves: %s", getMoveCount()),115,400);
        batch.draw(retryBlk, 115,280,230,100);
    }

    private void drawToolbar(SpriteBatch batch) {
        batch.draw(whiteBlk,0,730, 480,70);

        if(playSound) {
            batch.draw(volumeBlk, 390, 740, 50, 50);
        }else {
            batch.draw(muteBlk, 390, 740, 50, 50);
        }

        batch.draw(settingsBlk, 320, 740, 50, 50);
    }

    private Sprite retrieveSprite(int value){
        switch (value){
            case 2:
                return nb2Blk;
            case 4:
                return nb4Blk;
            case 8:
                return nb8Blk;
            case 16:
                return nb16Blk;
            case 32:
                return nb32Blk;
            case 64:
                return nb64Blk;
            case 128:
                return nb128Blk;
            case 256:
                return nb256Blk;
            case 512:
                return nb512Blk;
            case 1024:
                return nb1024Blk;
            case 2048:
                return nb2048lk;
            default:
                return nb0Blk;

        }
    }

    public boolean didGameBoardChange(List<NumberBlock> oldBoard, List<NumberBlock> newBoard){
        for (int i = 0; i < oldBoard.size(); i++) {
            if(oldBoard.get(i).get_value() != newBoard.get(i).get_value())
            {
                return true;
            }
        }

        return false;
    }

    public static void setNumberBlock(List<NumberBlock> board){
        int occurred = 0;

        do {
            int row = generateRandPos( 4 ), col = generateRandPos(4);

            NumberBlock nb = searchArray(row, col, board);
            if (nb.get_value() == 0) {
                nb.set_value(generateRandValue());
                break;
            }

            if(occurred > 2){
                NumberBlock eb = findBlock(board, 0);
                if(eb != null) {
                    eb.set_value(generateRandValue());
                    break;
                }else{
                    break;
                }
            }

            occurred ++;
        }while(true);
    }

    public static NumberBlock findBlock(List<NumberBlock> gameBoard, int value){

        for (NumberBlock blk: gameBoard) {
            if(blk.get_value() == value){
                return blk;
            }
        }

        return null;
    }

    public static List<NumberBlock> copyGameBoard(List<NumberBlock> gameBoard){
        List<NumberBlock> newList = new ArrayList<>();

        for (NumberBlock blk: gameBoard) {
            NumberBlock cpBlk = new NumberBlock(blk.get_column(), blk.get_row(), blk.get_value(), blk.get_state());
            newList.add(cpBlk);
        }

        return newList;
    }

    public boolean executeShift(String shiftDir, List<NumberBlock> tokens){
        int searchDir, startValue;
        boolean lockRow = true;

        switch (shiftDir) {
            case "east":
            case "south":
                searchDir = -1;
                startValue = 4;
                break;
            default:
                searchDir = 1;
                startValue = 1;
                break;
        }

        switch (shiftDir) {
            case "north":
            case "south":
                lockRow = false;
                break;
        }

        for(int x = startValue;x > 0 && x < 5; x = x + searchDir) {
            for(int y = startValue; y > 0 && y < 5; y = y + searchDir) {
                if(lockRow){
                    if (assignShiftValue(
                            searchArray(x, y, tokens),
                            searchArray(x, y + searchDir, tokens))) {
                        return true;  //break when a match is found.  The function will get called
                        // again until all matches are found.   This gives appearance of tiles sliding.
                    }
                }else{
                    if(assignShiftValue(searchArray(y, x, tokens),
                            searchArray(y +searchDir, x, tokens))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void dispose(){
        collapsedSound.dispose();
        textureAtlas.dispose();
    }

    private static NumberBlock searchArray(int row, int col, List<NumberBlock> blks){
        try {
            List<NumberBlock> searchBlk = new ArrayList<>();

            //hard coding gameboard indexes TODO:  This will have to be dynamic when introducing different game modes
            //TODO: Is there an easy way keep track of the rows that doesn't inolve spliting?
            if (row > 0) {
                switch (row) {
                    case 1:
                        searchBlk = blks.subList(0, 4);
                        break;
                    case 2:
                        searchBlk = blks.subList(4, 8);
                        break;
                    case 3:
                        searchBlk = blks.subList(8, 12);
                        break;
                    case 4:
                        searchBlk = blks.subList(12, 16);
                        break;
                }
            }

            if (col - 1 < 0) return new NumberBlock(0, 0, 0, "");

            return searchBlk.get(col - 1);
        }catch (IndexOutOfBoundsException io){
            return new NumberBlock(0, 0, 0, "");
        }
    }

    private boolean assignShiftValue(NumberBlock nb, NumberBlock nextBlock){
        if(nextBlock != null){

            if(nextBlock.get_value() == 0){
                return false;
            }

            if(nextBlock.get_value() == nb.get_value() && nb.get_state().equals("u")){
                nb.set_value(nb.get_value() * 2);
                nextBlock.set_value(0);
                nb.set_state("c");
                return true;
            }

            if(nb.get_value() == 0 && nextBlock.get_value() != 0){
                nb.set_value(nextBlock.get_value());
                nextBlock.set_value(0);
                return true;
            }
        }

        return false;
    }

    private static int generateRandValue(){
        int min =2, max =4,  range = max - min;
        int rand;

        do {
            rand = (int) (Math.random() * range) + min;
        }while (rand == 3);

        return rand;
    }

    private static int generateRandPos(Integer maxColumns){
        int min =1, max =maxColumns,  range = max - min;

        return (int)(Math.random() * range) + min;
    }
}
