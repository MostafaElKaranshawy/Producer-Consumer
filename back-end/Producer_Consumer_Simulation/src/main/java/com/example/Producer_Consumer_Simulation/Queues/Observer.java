package com.example.Producer_Consumer_Simulation.Queues;

import com.example.Producer_Consumer_Simulation.Machines.Observable;

public interface Observer {
    void update(Observable observable);
}
