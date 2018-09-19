package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {

    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles =  new Tile[FIELD_WIDTH][FIELD_WIDTH];
    int score;
    int maxTile;
    private Stack<Tile[][]> previousStates = new Stack();
    private Stack<Integer> previousScores = new Stack();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
        score = 0;
        maxTile = 0;
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove(){
        if (!getEmptyTiles().isEmpty()) {
            return true;
        }
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH - 1; j++) {
                if (gameTiles[i][j].value == gameTiles[i][j + 1].value) {
                    return true;
                }
            }
        }
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH - 1; j++) {
                if (gameTiles[j][i].value == gameTiles[j + 1][i].value) {
                    return true;
                }
            }
        }
        return false;
    }

    void resetGameTiles() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++ ) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    private List<Tile> getEmptyTiles(){
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].value == 0) result.add(gameTiles[i][j]);
            }
        }
        return result;
    }

    private void addTile(){
        List<Tile> emptyTiles = getEmptyTiles();
        if (emptyTiles != null  && !emptyTiles.isEmpty()) {
            int newTileIndex = (int) (emptyTiles.size() * Math.random());
            int newWeight = Math.random() < 0.9 ? 2 : 4;
            emptyTiles.get(newTileIndex).setValue(newWeight);
        }

    }

    private boolean compressTiles(Tile[] tiles) {
        boolean wasChanged = false;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].value == 0 && i < tiles.length - 1 && tiles[i + 1].value != 0) {
                Tile temp = tiles[i];
                tiles[i] = tiles[i + 1];
                tiles[i + 1] = temp;
                i = -1;
                wasChanged = true;
            }
        }
        return wasChanged;
    }

    private boolean mergeTiles(Tile[] tiles){
        boolean wasChanged = false;
        for (int i = 0; i < tiles.length - 1; i++) {
            if (tiles[i].value != 0 && (tiles[i].value == tiles[i + 1].value)) {
                if (tiles[i].value + tiles[i + 1].value > maxTile) {
                    maxTile = tiles[i].value + tiles[i + 1].value;
                }
                tiles[i].value = tiles[i].value + tiles[i + 1].value;
                score += tiles[i].value;
                tiles[i + 1].value = 0;
                wasChanged = true;
                compressTiles(tiles);
            }
        }
        return wasChanged;
    }

    public void left() {
        if (isSaveNeeded) {
            saveState(gameTiles);
        }
        boolean wasChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                wasChanged = true;
            }
        }
        if (wasChanged) {
            addTile();
        }
        isSaveNeeded = true;
    }

    public void right(){
        saveState(gameTiles);
        rotate();
        rotate();
        left();
        rotate();
        rotate();
    }

    public void up() {
        saveState(gameTiles);
        rotate();
        left();
        rotate();
        rotate();
        rotate();
    }

    public void down(){
        saveState(gameTiles);
        rotate();
        rotate();
        rotate();
        left();
        rotate();
    }

    private void rotate() {
        for (int i = 0; i < 2; i++) {
            for (int j = i; j < 3 - i; j++) {
                Tile tmp = gameTiles[i][j];
                gameTiles[i][j] = gameTiles[j][3 - i];
                gameTiles[j][3 - i] = gameTiles[3 - i][3 - j];
                gameTiles[3 - i][3 - j] = gameTiles[3 - j][i];
                gameTiles[3 - j][i] = tmp;
            }
        }
    }

    private void saveState (Tile[][] tiles) {
        Tile[][] tilesToSave = new Tile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[0].length; j++) {
                tilesToSave[i][j] = new Tile(tiles[i][j].getValue());
            }
        }

        previousStates.push(tilesToSave);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousScores.empty() && !previousStates.empty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    public void randomMove(){
        int direction = ((int) (Math.random() * 100)) % 4;
        switch (direction){
            case 0:
                left();
                break;
            case 1:
                right();
                break;
            case 2:
                up();
                break;
            case 3:
                down();
                break;
        }
    }

    private boolean hasBoardChanged() {
        int currentSum = 0;
        int previousSum = 0;
        Tile[][] previousState = previousStates.peek();
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[0].length; j++) {
                    currentSum += gameTiles[i][j].getValue();
                    previousSum += previousState[i][j].getValue();
                }
            }
        return currentSum != previousSum;
    }

    private MoveEfficiency getMoveEfficiency(Move move) {
        MoveEfficiency moveEfficiency;
        move.move();
        if (hasBoardChanged()){
            moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);
        } else {
            moveEfficiency = new MoveEfficiency(-1, 0, move);
        }
        rollback();
        return moveEfficiency;
    }

    public void autoMove(){
        PriorityQueue<MoveEfficiency> efficiencyPriorityQueue = new PriorityQueue<>(4, Collections.reverseOrder());
        efficiencyPriorityQueue.add(getMoveEfficiency(this::left));
        efficiencyPriorityQueue.add(getMoveEfficiency(this::right));
        efficiencyPriorityQueue.add(getMoveEfficiency(this::up));
        efficiencyPriorityQueue.add(getMoveEfficiency(this::down));
        efficiencyPriorityQueue.peek().getMove().move();
    }

}
