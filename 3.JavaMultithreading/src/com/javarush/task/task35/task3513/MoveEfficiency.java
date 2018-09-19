package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency>{

    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency other) {
//        if (numberOfEmptyTiles != other.numberOfEmptyTiles) {
//            return numberOfEmptyTiles - other.numberOfEmptyTiles;
//        }
//        return score - other.score;

        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this == other) return EQUAL;

        if (this.numberOfEmptyTiles > other.numberOfEmptyTiles) return AFTER;
        if (this.numberOfEmptyTiles < other.numberOfEmptyTiles) return BEFORE;

        if (this.numberOfEmptyTiles == other.numberOfEmptyTiles) {
            if (this.score > other.score) return AFTER;
            if (this.score < other.score) return BEFORE;
        }
        return EQUAL;

    }
}