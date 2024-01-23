package com.example.Producer_Consumer_Simulation.Queues;

import com.example.Producer_Consumer_Simulation.Machines.Machine;
import com.example.Producer_Consumer_Simulation.Machines.Observable;
import com.example.Producer_Consumer_Simulation.Product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ProductsQueue implements Observer{
    private ArrayList<Observable> observableMachines;
    private int id;
    private Queue<Product> productsToProcess;

    public ProductsQueue(int id){
        this.observableMachines = new ArrayList<>(5);
        this.id = id;
        productsToProcess = new LinkedList<>();
    }
    public int getId() {
        return id;
    }

    public ArrayList<Observable> getObservableMachines() {
        return observableMachines;
    }

    public void setObservableMachines(ArrayList<Observable> observableMachines) {
        this.observableMachines = observableMachines;
    }

    public Queue<Product> getProductsToProcess() {
        return productsToProcess;
    }

//    public void setProductsToProcess(Queue<Product> productsToProcess) {
//        this.productsToProcess = productsToProcess;
//    }

    public void addProduct(Product product){
        this.productsToProcess.add(product);
    }

    public void addObservableMachine(Observable observableMachine){
        this.observableMachines.add(observableMachine);
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getNumberOfProducts(){
        return this.productsToProcess.size();
    }

    public synchronized Product sendNewProduct(){
        return this.productsToProcess.poll();
    }
    @Override
    public void update(Observable observable) {
        ( (Machine) observable).takeNewProduct();
    }

    @Override
    public String toString() {
        return "ProductsQueue{" +
                "observableMachines=" + observableMachines +
                ", id=" + id +
                ", productsToProcess=" + productsToProcess +
                '}';
    }
}