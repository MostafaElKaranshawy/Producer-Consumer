package com.example.Producer_Consumer_Simulation;

import com.example.Producer_Consumer_Simulation.Machines.Machine;
import com.example.Producer_Consumer_Simulation.Queues.ProductsQueue;
import com.example.Producer_Consumer_Simulation.SnapShot.CareTaker;
import com.example.Producer_Consumer_Simulation.SnapShot.Memento;

import java.util.ArrayList;

public class SimulationSystem {
    private static SimulationSystem system;
    private ArrayList<Thread> threads;
    private ArrayList<Machine> machines;
    private ArrayList<ProductsQueue> queues;
    private int numberOfProducts;
    private ArrayList<String> productColors;
    private CareTaker careTaker;
    private SimulationSystem(){
        this.threads = new ArrayList<>();
        this.machines = new ArrayList<>();
        this.queues = new ArrayList<>();
        this.productColors = new ArrayList<>();
        this.careTaker = CareTaker.getCareTaker();

    }
    public static synchronized SimulationSystem getInstance(){
        if (system == null)
            system = new SimulationSystem();
        return system;
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public void setMachines(ArrayList<Machine> machines) {
        this.machines = machines;
    }

    public ArrayList<ProductsQueue> getQueues() {
        return queues;
    }

    public void setQueues(ArrayList<ProductsQueue> queues) {
        this.queues = queues;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
        for(int i = 0; i< numberOfProducts; i++){
            Product product = new Product();
            this.productColors.add(product.getColor());
            queues.get(0).addProduct(product);
        }
    }

    public void addMachine(int id){
        machines.add(new Machine(id));
    }
    public void addQueue(int id){
        queues.add(new ProductsQueue(id));
    }
    private Machine getMachineById(int machineId){
        for(Machine machine : machines){
            if (machine.getId() == machineId)
                return machine;
        }
        return null;
    }
    private ProductsQueue getQueueById(int queueId){
        for(ProductsQueue queue : queues){
            if (queue.getId() == queueId)
                return queue;
        }
        return null;
    }
    public void connectQueueToMachine(int queueId, int machineId){
        ProductsQueue specificQueue = getQueueById(queueId);
        Machine specificMachine = getMachineById(machineId);
        specificMachine.addObserver(specificQueue);
        specificQueue.addObservableMachine(specificMachine);
    }

    public void connectMachineToQueue(int queueId, int machineId){
        Machine specificMachine = getMachineById(machineId);
        ProductsQueue specificQueue = getQueueById(queueId);
        specificMachine.setSuccessorQueue(specificQueue);
    }
//
//    public void deleteConnectionFromQueueToMachine(int queueId, int machineId){
//        ProductsQueue specificQueue = getQueueById(queueId);
//        Machine specificMachine = getMachineById(machineId);
//        for(Observer observer : specificMachine.getObservers()){
//            ProductsQueue productsQueue = (ProductsQueue) observer;
//            if (productsQueue.getId() == queueId) {
//                specificMachine.getObservers().remove(productsQueue);  /////////////////////////
//                break;
//            }
//        }
//        for (Observable observable : specificQueue.getObservableMachines()){
//            Machine machine = (Machine) observable;
//            if (machine.getId() == machineId) {
//                specificQueue.getObservableMachines().remove(machine);  /////////////////////////
//                break;
//            }
//        }
//
//
//    }
//    public void deleteConnectionFromMachineToQueue(int queueId, int machineId){
//        this.connectMachineToQueue(-1,machineId);
//    }
//    public void deleteMachine(int machineId){
//        Machine specificMachine = getMachineById(machineId);
//        for (Observer observer : specificMachine.getObservers()){
//            ProductsQueue queueOfObserversToSearchIn = (ProductsQueue) observer;
//            for (Observable observable : queueOfObserversToSearchIn.getObservableMachines()){
//                Machine machine = (Machine) observable;
//                if (machine.getId() == machineId) {
//                    queueOfObserversToSearchIn.getObservableMachines().remove(machine);  /////////////////////////
//                    break;
//                }
//            }
//        }
//        this.machines.remove(specificMachine);
//    }
//    public void deleteQueue(int queueId){
//        ProductsQueue specificQueue = getQueueById(queueId);
//        for (Observable observable : specificQueue.getObservableMachines()){
//            Machine machine = (Machine) observable;
//            for(Observer observer : machine.getObservers()){
//                ProductsQueue queueToCheck = (ProductsQueue) observer;
//
//                if (queueToCheck.getId() == queueId) {
//                    machine.getObservers().remove(queueToCheck);  /////////////////////////
//                    break;
//                }
//            }
//        }
//        this.queues.remove(specificQueue);
//    }
    public void clear(){
        this.threads = new ArrayList<>();
        this.machines = new ArrayList<>();
        this.queues = new ArrayList<>();
        this.numberOfProducts = 0;
        this.careTaker.deleteMementos();
        this.productColors = new ArrayList<>();
    }
    public void makeMementosReady(){
        CareTaker careTaker = CareTaker.getCareTaker();
        careTaker.deleteMementos();
        Memento firstMemento =  new Memento();
        for(int machineCount = 0; machineCount < system.getMachines().size(); machineCount++){
            firstMemento.addColor("#808080");
        }
        for(int queueCount = 0 ; queueCount < system.getQueues().size(); queueCount++){
            firstMemento.addQueueSize(0);
        }
        firstMemento.getQueueSizes().set( 0 , system.getNumberOfProducts() );
        careTaker.addMemento(firstMemento);
    }

    public void reSimulate(){
        for(int i = 0; i < this.numberOfProducts;i++) {
            this.queues.get(this.queues.size()-1).getProductsToProcess().poll();
            this.queues.get(0).getProductsToProcess().add(
                    new Product(this.productColors.get(i))
            );
        }
        this.makeMementosReady();
        this.threads.clear();
        System.out.println(  this.careTaker.getMementos().size());
        this.simulate();
    }


    public void simulate(){
        System.out.println("colors:  "+this.productColors.size());
        System.out.println(this.queues.get(0).getProductsToProcess().toString());
        for (Machine machine: this.machines){
            Thread thread = new Thread(machine);
            //machine.setOnState(true);
            threads.add(thread);
        }
        System.out.println(this.queues.get(0).getObservableMachines().size());
        System.out.println(this.threads);
        for (Thread thread: this.threads){
            thread.start();
        }
    }

}
