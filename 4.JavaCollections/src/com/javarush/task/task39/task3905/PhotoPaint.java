package com.javarush.task.task39.task3905;

public class PhotoPaint {

    static boolean[][] visited;

    public boolean paintFill(Color[][] image, int x, int y, Color desiredColor) {
        boolean result = true;
        if (! correctCoordinates(x, y, image)) {
            result = false;
        } else if (image[y][x] == desiredColor) {
            result = false;
        } else {
            visited = new boolean[image[0].length][image.length];
            fill(image, x, y, desiredColor, image[y][x]);
        }
        return result;
    }

    private void fill(Color[][] image, int x, int y, Color desiredColor, Color currentColor) {
        if (image[y][x] != desiredColor) {
            visited[y][x] = true;
            image[y][x] = desiredColor;
            if (canFill(x - 1, y, image, currentColor)) {
                fill(image, x - 1, y, desiredColor, currentColor);
            }
            if (canFill(x + 1, y, image, currentColor)) {
                fill(image, x + 1, y, desiredColor, currentColor);
            }
            if (canFill(x , y - 1, image, currentColor)) {
                fill(image, x, y - 1, desiredColor, currentColor);
            }
            if (canFill(x, y + 1, image, currentColor)) {
                fill(image, x, y + 1, desiredColor, currentColor);
            }
        }
    }

    private boolean canFill(int x, int y, Color[][] image, Color currentColor) {
        return correctCoordinates(x, y, image) && !visited[y][x] && image[y][x] == currentColor;
    }

    private boolean correctCoordinates(int x, int y, Color[][] image) {
        return x >= 0 && y >= 0 && x < image.length && y < image[0].length;
    }

}
