package com.example.Producer_Consumer_Simulation;

import java.util.Random;

public class Product {
    private String color;
    Product(){
        this.color = generateRandomHexColor();
    }
    Product(String color){
        this.color = color;
    }
    private String generateRandomHexColor() {
        Random random = new Random();

        int red = random.nextInt(230);
        int green = random.nextInt(230);
        int blue = random.nextInt(230);

        int variation = 100; // Adjust this value to control the amount of variation
        red = clamp(red + random.nextInt(variation * 2 + 1) - variation);
        green = clamp(green + random.nextInt(variation * 2 + 1) - variation);
        blue = clamp(blue + random.nextInt(variation * 2 + 1) - variation);



        String hexColor = String.format("#%02X%02X%02X", red, green, blue);
        return hexColor;
    }
    // Ensure that the color component is within the valid range [0, 255]
    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Product{" +
                "color='" + color + '\'' +
                '}';
    }
}
