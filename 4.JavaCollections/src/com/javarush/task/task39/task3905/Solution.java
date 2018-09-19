package com.javarush.task.task39.task3905;

/* 
Залей меня полностью
*/

public class Solution {
    public static void main(String[] args) {
        PhotoPaint photoPaint = new PhotoPaint();

        Color[][] image = {
                {Color.YELLOW, Color.YELLOW, Color.YELLOW},
                {Color.YELLOW, Color.RED, Color.YELLOW},
                {Color.YELLOW, Color.YELLOW, Color.YELLOW}};

        showImage(image);
        photoPaint.paintFill(image, 1, 1, Color.RED);
        showImage(image);
        photoPaint.paintFill(image, 1, 1, Color.GREEN);
        showImage(image);
        photoPaint.paintFill(image, 0, 0, Color.GREEN);
        showImage(image);

    }

    private static void showImage(Color[][]image) {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                System.out.print(image[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
