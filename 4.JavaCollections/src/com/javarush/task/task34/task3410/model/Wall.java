package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Wall extends CollisionObject {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(new Color(168,110, 30));
        graphics.drawRect(getX(), getY(), getWidth(), getHeight());
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
