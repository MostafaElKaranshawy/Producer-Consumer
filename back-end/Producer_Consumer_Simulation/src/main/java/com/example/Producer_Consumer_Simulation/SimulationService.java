package com.example.Producer_Consumer_Simulation;

import com.example.Producer_Consumer_Simulation.SnapShot.CareTaker;
import com.example.Producer_Consumer_Simulation.SnapShot.Memento;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SimulationService {
    SimulationService service;
    private SimulationSystem system;

    public void addMachineToSystem(int id){
        system = SimulationSystem.getInstance();
        system.addMachine(id);
    }
    public void addQueueToSystem(int id){
        system = SimulationSystem.getInstance();
        system.addQueue(id);
    }
    private void connectQueueToMachineInSystem(int queueId, int machineId){
        system.connectQueueToMachine(queueId, machineId);
    }

    private void connectMachineToQueueInSystem(int machineId, int queueId){
        system.connectMachineToQueue(queueId, machineId);
    }
    public void addConnectionToSystem(SystemDto systemDto){
        if(systemDto.getSource().equals("machine"))
            this.connectMachineToQueueInSystem(systemDto.getFromId(), systemDto.getToId());
        else if (systemDto.getSource().equals("queue"))
            this.connectQueueToMachineInSystem(systemDto.getFromId(), systemDto.getToId());
    }
    public ArrayList<Memento> sendingUpdates(){
        return CareTaker.getCareTaker().getMementos();
    }
//
//    public void deleteConnectionFromQueueToMachineInSystem(int queueId, int machineId){
//        system.deleteConnectionFromQueueToMachine(queueId, machineId);
//    }
//    // don't forget to make the delete connection between machine and queue function , delete queue, delete machine
//    public void deleteConnectionFromMachineToQueueInSystem(int queueId, int machineId){
//        system.deleteConnectionFromMachineToQueue(queueId, machineId);
//    }
//    public void deleteMachineInSystem(int machineId){
//        system.deleteMachine(machineId);
//    }
//    public void deleteQueue(int queueId){
//        system.deleteQueue(queueId);
//    }


    public void startSimulationSystem(int numberOfProducts) {

        this.setNumberOfProductsInSystem(numberOfProducts);
        this.system.makeMementosReady();


        this.system.simulate();
    }

    public void setNumberOfProductsInSystem(int numberOfProducts){
        system.setNumberOfProducts(numberOfProducts);
    }

    public void clearSystem(){
        system.clear();
    }
    public void reSimulateSystem(){
        system.reSimulate();

    }



}
