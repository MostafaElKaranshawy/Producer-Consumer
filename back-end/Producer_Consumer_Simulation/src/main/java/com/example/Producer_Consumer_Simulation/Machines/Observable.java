package com.example.Producer_Consumer_Simulation.Machines;

import com.example.Producer_Consumer_Simulation.Queues.Observer;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers();
}
