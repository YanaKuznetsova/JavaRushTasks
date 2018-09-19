package com.javarush.task.task34.task3410.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.javarush.task.task34.task3410.model.Model.FIELD_CELL_SIZE;

public class LevelLoader {

    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        if (level > 60) {
            level = level % 60;
        }

        char[][] fieldMap = new char[0][0];
        try (BufferedReader reader = new BufferedReader(new FileReader(levels.toFile()))) {
            int currentLevel = 0;
            int sizeX, sizeY;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Maze:")) {
                    currentLevel = Integer.valueOf(line.split(" ")[1]);
                    continue;
                }
                if (currentLevel == level) {
                    break;
                }
            }
            //reader.readLine(); // file offset;
            sizeX = Integer.valueOf(reader.readLine().split(" ")[2]);
            sizeY = Integer.valueOf(reader.readLine().split(" ")[2]);
            reader.readLine(); // end
            reader.readLine(); // length
            reader.readLine(); // skip line
            fieldMap = new char[sizeY][sizeX];
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    fieldMap[i][j] = (char) reader.read();
                }
                reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createGameObjects(fieldMap);
    }

    private GameObjects createGameObjects(char[][] fieldMap){
        int step = FIELD_CELL_SIZE / 2;
        Player player = null;
        Set<Home> homes = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Wall> walls = new HashSet<>();
        int x;
        int y = step;
        for (int i = 0; i < fieldMap.length; i++) {
            x = step;
            for (int j = 0; j < fieldMap[0].length; j++) {
                switch (fieldMap[i][j]) {
                    case 'X':
                        walls.add(new Wall(x, y));
                        break;
                    case '*':
                        boxes.add(new Box(x, y));
                        break;
                    case '.':
                        homes.add(new Home(x, y));
                        break;
                    case '&':
                        boxes.add(new Box(x, y));
                        homes.add(new Home(x, y));
                        break;
                    case '@':
                        player = new Player(x, y);
                }
                x += 2 * step;
                }
            y += 2 * step;
        }

        return new GameObjects(walls, boxes, homes, player);
    }

}
