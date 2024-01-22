package com.example.Producer_Consumer_Simulation.Machines;

import com.example.Producer_Consumer_Simulation.Product;
import com.example.Producer_Consumer_Simulation.Queues.Observer;
import com.example.Producer_Consumer_Simulation.Queues.ProductsQueue;
import com.example.Producer_Consumer_Simulation.SimulationSystem;
import com.example.Producer_Consumer_Simulation.SnapShot.CareTaker;
import com.example.Producer_Consumer_Simulation.SnapShot.Memento;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Machine implements Observable, Runnable {
    private int id;
    private int processingRate;
    private ArrayList<Observer> observers;
    private boolean isReady;
    private ProductsQueue successorQueue;
    private Product inOutProduct;
    private CareTaker careTaker;
    private ProductsQueue lastSupplier;

    public Machine(int id) {
        final int maxRate = 10000;
        final int minRate = 3000;
        this.processingRate = new Random().nextInt(maxRate - minRate + 1) + minRate;
        System.out.println("machine: "+ this.id + " takes time " +this.processingRate);
        careTaker = CareTaker.getCareTaker();
        this.observers = new ArrayList<>(5);
        this.id = id;
        this.isReady = true;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this);
//            System.out.println("observer id "+((ProductsQueue) observer).getId());
            if (inOutProduct != null) {
                this.lastSupplier = (ProductsQueue) observer;
                return;
            }
        }
    }

    public int getId() {
        return id;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public ProductsQueue getSuccessorQueue() {
        return successorQueue;
    }

    public void setSuccessorQueue(ProductsQueue successorQueue) {
        this.successorQueue = successorQueue;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }

    public synchronized void takeNewProduct() {
        Product tempProduct = null;
        for (Observer observer : observers) {
            if (((ProductsQueue) observer).getProductsToProcess().isEmpty())
                continue;
            else {
                tempProduct = ((ProductsQueue) observer).sendNewProduct();
                break;
            }
        }
        inOutProduct = tempProduct;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", processingRate=" + processingRate +
                ", observers=" + observers +
                ", isReady=" + isReady +
                ", successorQueue=" + successorQueue +
                ", inOutProduct=" + inOutProduct +
                '}';
    }

    @Override
    public void run() {
        while (true) {
            try {
                Memento machineChanges = null;
//              System.out.println("hello from thread " + Thread.currentThread());
                this.notifyObservers();
//                System.out.println(Thread.currentThread() + " before sync ");
                if(inOutProduct != null){
                    synchronized(this){
                        System.out.println(Thread.currentThread() + " inside sync ");
                        machineChanges = careTaker.cloneLastMemento();
        //              System.out.println(Thread.currentThread() + " Last Memento at the start of processing: " + machineChanges.toString());

                        machineChanges.getMachineColors().set(this.id, inOutProduct.getColor());
                        machineChanges.getQueueSizes().set(this.lastSupplier.getId(), this.lastSupplier.getNumberOfProducts());
                        careTaker.addMemento(machineChanges);  //[1,0,0
                    }
                }
                if(inOutProduct != null){
                    this.isReady = false;
                    System.out.println(Thread.currentThread() + " created Memento before sleep: " + machineChanges.toString());

                    Thread.sleep(this.processingRate);

                    machineChanges = careTaker.cloneLastMemento();
                    machineChanges.getMachineColors().set(this.id, "#808080");

                    machineChanges.getQueueSizes().set(this.successorQueue.getId(), this.successorQueue.getNumberOfProducts()+1);
                    System.out.println(Thread.currentThread() + " created Memento at the end of processing: " + machineChanges.toString());
                    careTaker.addMemento(machineChanges);  //[1,1,0]
                    this.successorQueue.addProduct(inOutProduct); //[1,1,0]
//                    System.out.println(Thread.currentThread() + " last Memento at the end of processing after creation of momento: " + machineChanges.toString());
                }
            }catch (InterruptedException e) {
            }
            SimulationSystem system = SimulationSystem.getInstance();

            if (system.getQueues().get(system.getQueues().size() - 1).getProductsToProcess().size() == system.getNumberOfProducts()) {
                System.out.println("finished from " + Thread.currentThread());
                break;
            }

        }
    }
}