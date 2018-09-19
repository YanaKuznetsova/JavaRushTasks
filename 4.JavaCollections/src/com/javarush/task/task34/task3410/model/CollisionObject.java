package com.javarush.task.task34.task3410.model;

import java.awt.*;

import static com.javarush.task.task34.task3410.model.Model.FIELD_CELL_SIZE;

public abstract class CollisionObject extends GameObject{

    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction){
        int newX = this.getX();
        int newY = this.getY();
        switch (direction){
            case UP:
                newY -= FIELD_CELL_SIZE;
                break;
            case DOWN:
                newY += FIELD_CELL_SIZE;
                break;
            case LEFT:
                newX -= FIELD_CELL_SIZE;
                break;
            case RIGHT:
                newX += FIELD_CELL_SIZE;
                break;
        }
        return newX == gameObject.getX() && newY == gameObject.getY();
    }

}
