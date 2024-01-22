package com.example.Producer_Consumer_Simulation;

import java.util.Random;

public class Product {
    private String color;
    Product(){
        this.color = generateRandomHexColor();
    }
    private String generateRandomHexColor() {
        Random random = new Random();
        int red = random.nextInt(230);
        int green = random.nextInt(230);
        int blue = random.nextInt(230);
        String hexColor = String.format("#%02X%02X%02X", red, green, blue);
        return hexColor;
    }
    public String getColor() {
        return color;
    }
}
