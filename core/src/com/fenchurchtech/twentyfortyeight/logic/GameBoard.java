package com.fenchurchtech.twentyfortyeight.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameBoard {

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

    public static void draw(List<NumberBlock> blocks, SpriteBatch batch){
        batch.begin();
        float x = 0.0f, y =600.0f;

        Texture bkgrd = new Texture( Gdx.files.internal("background.png"));
        Texture titleBlk = new  Texture(Gdx.files.internal("Title.png"));

        batch.draw(bkgrd, 0,0,480,800);
        batch.draw(titleBlk, 10,730,216,72);

        for (NumberBlock nb: blocks
        ) {

            if(nb.get_column() == 1){
                x = 50;
                y= y - 95;
            }

            batch.draw(nb.get_texture(), x, y,95,95);
            x = x + 95;
        }

        batch.end();
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
                NumberBlock eb = findZeroBlock(board);
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

    public static NumberBlock findZeroBlock(List<NumberBlock> gameBoard){

        for (NumberBlock blk: gameBoard) {
            if(blk.get_value() == 0){
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
/*                        tokens.stream().filter(p -> p.get_column() == iter).forEach((nb) -> {
                            Optional<NumberBlock> nextBlock = tokens.stream().filter((p) -> p.get_column() == iter - 1 && p.get_row() == nb.get_row()).findFirst();
                            if(assignShiftValue(nb, nextBlock)){
                                executeShift(shiftDir, tokens);
                            }
                        });*/
                        for(int col = 4; col > 0; col--) {
                            if (assignShiftValue(searchArray(i, col, tokens), searchArray(i, col - 1, tokens))) {
                               return true;
                            }
                        }

                    }
                    if(shiftDir.equals("south")){

                       /* tokens.stream().filter(p -> p.get_row() == iter).forEach((nb) -> {

                            Optional<NumberBlock> nextBlock = tokens.stream().filter((p) -> p.get_row() == iter - 1 && p.get_column() == nb.get_column()).findFirst();

                            if(assignShiftValue(nb, nextBlock)){
                                executeShift(shiftDir, tokens);
                            }
                        });*/
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
