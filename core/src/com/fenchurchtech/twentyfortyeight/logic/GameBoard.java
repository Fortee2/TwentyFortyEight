package com.fenchurchtech.twentyfortyeight.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameBoard {
    private final Texture bkgrd = new Texture( Gdx.files.internal("background.png"));
    private final Texture titleBlk = new  Texture(Gdx.files.internal("Title.png"));
   // private final Texture exitBlk = new  Texture(Gdx.files.internal("Exit.png"));
    private final Texture nb0Blik  = new Texture(Gdx.files.internal("blank.png"));
    private final Texture  settingsBlik = new Texture(Gdx.files.internal("settings.png"));
    private final Texture nb2Blk = new  Texture(Gdx.files.internal("2.png"));
    private final Texture nb4Blk = new  Texture(Gdx.files.internal("4.png"));
    private final Texture nb8Blk = new  Texture(Gdx.files.internal("8.png"));
    private final Texture nb16Blk = new  Texture(Gdx.files.internal("16.png"));
    private final Texture nb32Blk = new  Texture(Gdx.files.internal("32.png"));
    private final Texture nb64Blk = new  Texture(Gdx.files.internal("64.png"));
    private final Texture nb128Blk = new  Texture(Gdx.files.internal("128.png"));
    private final Texture nb256Blk = new  Texture(Gdx.files.internal("256.png"));
    private final Texture nb512Blk = new  Texture(Gdx.files.internal("512.png"));
    private final Texture nb1024Blk = new  Texture(Gdx.files.internal("1024.png"));
    private final Texture nb2048lk = new  Texture(Gdx.files.internal("2048.png"));

    public static List<NumberBlock> initializeBlocks(int maxRowsCols){
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

    public  void draw(List<NumberBlock> blocks, SpriteBatch batch){
        batch.begin();
        float x = 0.0f, y =600.0f;

        batch.draw(bkgrd, 0,0,480,800);
        batch.draw(titleBlk, 10,730,216,72);
        batch.draw(settingsBlik, 390,730,50,50);

        for (NumberBlock nb: blocks
        ) {

            if(nb.get_column() == 1){
                x = 50;
                y= y - 95;
            }

            batch.draw(retrieveTexture(nb.get_value()), x, y,95,95);
            x = x + 95;
        }

      /*  y = y - 105;
        batch.draw(exitBlk, 145, y);*/
        batch.end();
    }

    public Texture retrieveTexture(int value){
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
                return nb0Blik;

        }
    }

    public static boolean didGameBoardChange(List<NumberBlock> oldBoard, List<NumberBlock> newBoard){
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

    public static boolean executeShift(String shiftDir, List<NumberBlock> tokens){
        switch (shiftDir){
            case "east":
            case "south":
                for (int i = 4 ; i > 0; i--) {
                    final int  iter = i;

                    if(shiftDir.equals("east")) {
                        for(int col = 4; col > 0; col--) {
                            if (assignShiftValue(searchArray(i, col, tokens), searchArray(i, col - 1, tokens))) {
                               return true;
                            }
                        }
                    }
                    if(shiftDir.equals("south")){
                        for(int row = 4; row > 0; row--){
                            if(assignShiftValue(searchArray(row, i, tokens), searchArray(row-1, i, tokens))){
                                return true;
                            }
                        }
                    }
                }
            case "west":
            case "north":
                for (int i = 1; i < 5; i++){
                    final int  iter = i;
                    if(shiftDir.equals("west")) {
                        for(int col = 1; col < 5; col++) {
                            if (assignShiftValue(searchArray(i, col, tokens), searchArray(i, col + 1, tokens))) {
                                return true;
                            }
                        }
                    }
                    if(shiftDir.equals("north")){
                        for(int row = 1; row < 5; row++){
                            if(assignShiftValue(searchArray(row, i, tokens), searchArray(row + 1, i, tokens))){
                                return true;
                            }
                        }

                    }
                }
                break;
        }

        return false;
    }

    private static NumberBlock searchArray(int row, int col, List<NumberBlock> blks){
        try {
            List<NumberBlock> searchBlk = new ArrayList<>();

            //hard coding gameboard indexes
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

    private static boolean assignShiftValue(NumberBlock nb, NumberBlock nextBlock){
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
