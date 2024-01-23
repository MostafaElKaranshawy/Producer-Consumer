package com.example.Producer_Consumer_Simulation.SnapShot;

import java.util.ArrayList;

public class Memento {
    private ArrayList<String> machineColors;
    private ArrayList<Integer> queueSizes;

    public Memento(){
        this.machineColors = new ArrayList<>();
        this.queueSizes = new ArrayList<>();
    }
    public Memento(ArrayList<String> machineColors, ArrayList<Integer> queueSizes) {
        this.machineColors = machineColors;
        this.queueSizes = queueSizes;
    }

    public void addColor(String color){
        this.machineColors.add(color);
    }
    public void addQueueSize(int size){
        this.queueSizes.add(size);
    }

    public ArrayList<String> getMachineColors() {
        return machineColors;
    }

    public void setMachineColors(ArrayList<String> machineColors) {
        this.machineColors = machineColors;
    }

    public ArrayList<Integer> getQueueSizes() {
        return queueSizes;
    }

    public void setQueueSizes(ArrayList<Integer> queueSizes) {
        this.queueSizes = queueSizes;
    }
    public String toString() {
        // Create a meaningful string representation of the Memento's content
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Machine Colors: ").append(machineColors.toString()).append("  ");
        stringBuilder.append("Queue Sizes: ").append(queueSizes.toString());
        return stringBuilder.toString();
    }
}
