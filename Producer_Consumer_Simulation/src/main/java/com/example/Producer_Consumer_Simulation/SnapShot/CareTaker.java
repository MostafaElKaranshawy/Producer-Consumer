package com.example.Producer_Consumer_Simulation.SnapShot;

import java.util.ArrayList;
import java.util.Collection;

public class CareTaker {
    private ArrayList<Memento> mementos;
    private static CareTaker careTaker;

    private CareTaker(){}
    public static CareTaker getCareTaker() {
        if (careTaker == null){
            careTaker = new CareTaker();
            careTaker.mementos = new ArrayList<>();
        }
        return careTaker;
    }

    public ArrayList<Memento> getMementos() {
        return mementos;
    }

    public void setMementos(ArrayList<Memento> mementos) {
        this.mementos = mementos;
    }
    public void addMemento(Memento memento){
        this.getCareTaker().getMementos().add(memento);
    }

    public synchronized Memento cloneLastMemento() {
        return new Memento(
                new ArrayList<>( mementos.get(mementos.size()-1).getMachineColors()),
                new ArrayList<>( mementos.get(mementos.size()-1).getQueueSizes())
        );
    }
}
