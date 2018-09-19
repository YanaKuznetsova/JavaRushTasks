package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.nio.file.Paths;

public class Model {

    public static final int FIELD_CELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("4.JavaCollections\\src\\" +
            this.getClass().getPackage().getName().replace(".","\\").replace("model","")
            + "\\res\\levels.txt"));

    public GameObjects getGameObjects(){
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart(){
        restartLevel(currentLevel);
    }

    public void startNextLevel(){
        restartLevel(++currentLevel);
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
        if (!checkWallCollision(player, direction) && !checkBoxCollisionAndMoveIfAvaliable(direction)) {
            moveOneStepInDirection(player, direction);
            checkCompletion();
        }
    }

    private void moveOneStepInDirection(Movable movable, Direction direction) {
        switch (direction) {
            case LEFT:
                movable.move(-FIELD_CELL_SIZE, 0);
                break;
            case RIGHT:
                movable.move(FIELD_CELL_SIZE, 0);
                break;
            case UP:
                movable.move(0, -FIELD_CELL_SIZE);
                break;
            case DOWN:
                movable.move(0, FIELD_CELL_SIZE);
        }
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        boolean result = false;
        for (Wall wall: gameObjects.getWalls()) {
            result = result || gameObject.isCollision(wall, direction);
        }
        return result;
    }

    public boolean checkBoxCollisionAndMoveIfAvaliable(Direction direction) {
        boolean result = false;

        Player player = gameObjects.getPlayer();
        GameObject obstruction = null;
        for (GameObject gameObject : gameObjects.getAll()) {
            if (!(gameObject instanceof Player) && !(gameObject instanceof Home) && player.isCollision(gameObject, direction)) {
                obstruction = gameObject;
                break;
            }
        }

        if (obstruction instanceof Box) {
            Box obstructingBox = (Box) obstruction;
            if (checkWallCollision(obstructingBox, direction)) {
                result = true;
            } else {
                for (Box box : gameObjects.getBoxes()) {
                    if (obstructingBox.isCollision(box, direction)) {
                        result = true;
                        break;
                    }
                }
                if (! result) {
                    moveOneStepInDirection(obstructingBox, direction);
                }
            }
        }
        return result;
    }

    public void checkCompletion(){
        boolean levelComplete = true;
        for (Box box: gameObjects.getBoxes()){
            boolean boxIsAtHome = false;
            for (Home home : gameObjects.getHomes()) {
                if ((box.getX() == home.getX()) && (box.getY() == home.getY())) {
                    boxIsAtHome = true;
                }
            }
            if (!boxIsAtHome) {
                levelComplete = false;
            }
        }
        if (levelComplete) {
            eventListener.levelCompleted(currentLevel);
        }
    }

}
