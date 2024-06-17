# Paint
  Paint is a Web Application that used to create different shapes and the editing them.
  
## Table of Contents

- [Installation](#Installation) 🌐
- [Features](#Features)
- [Design Patterns](#Design-Patterns)
- [UML](#UML)
- [User Guide](#User-Guide)
- [User Interface](#User-Interface)
- [Tutorial](#Tutorial)

## Installation

To use the tools and examples provided in this repository, you need to have npm installed on your system. You can install the necessary dependencies by cloning the repository and running the installation command.

1. Clone the repository to your local machine.
2. Navigate to the repository directory.
3. Install the required dependencies.

## Features
- Simulation program that represents the sequence of product processing
- Adding any number of stages “Queues” and any number of processes “Machines”.
- The ability to restart the last simulation without need to create the system again.
- The ability to clear the current system and execute another one.
- GUI animations that helps the user to identify products at different stages of the process.

## UML
![image](https://github.com/MostafaElKaranshawy/Producer-Consumer/assets/110842052/f5440d54-3ba8-4ed2-a741-2e28839f89f2)

For Higher Quality:
(https://lucid.app/lucidchart/506df145-c24f-4126-a278-8a9723a28bb7/edit?view_items=vFi8li.ps7wM&invitationId=inv_7d9b55f5-4f49-45d8-a2ec-ff99d9244f71)

## Design-Patterns
From concurrency patterns:
  1. Single Threaded design pattern: we have applied it to prevent concurrent access to critical data (such as queues) throughout the program by the use of synchronised keyword
  2. Producer-Consumer design pattern: we have applied it To coordinate the asynchronous production and consumption of products by machines so that no two machines take the same product by mistake or no queue that supplies a non-existing product to a machine
Other design patterns:
  1. Singleton design pattern: we applied it to prevent the existence of multiple objects in memory of objects that must only be created once in the system such as the SimulationSystem class and the CareTaker class and also the SimulationService class
  2. Observer design pattern: we applied it through both the observer interface and its "update" method and the observable interface and its "addObserver, notifyObserver" methods so that when each machine finishes its processing on the product it notifies its observer (which is the queue supplying it with products) and after that the queues supply their observables (machines) with new products through the update method
  3. SnapShot design pattern: we applied it to save the the state of the system so that we can replay it again, we applied it by the implementation of the CareTaker class and the memento class where the memento represents the state saved and the CareTaker is the class that saves this memento represents (the originator class is our SimulationSystem class)

## User-Guide
  - Upon opening the application the drawing board is fully white and empty.
  - To create a machine or queue, click on the item in the sidebar then click at any
  place on the whiteboard.
  - To add a connection click on the connect icon in the sidebar, then choose the
  first machine/queue as a source, then it will be colored in red, then choose the
  second queue/machine.
  - You can choose the number of products from the toolbar.
  - To run the program press the Run button then the Queues and Machines will
  change their state according to the flow of the program.
  - You can replay the last operation using the Restart button.
  - You can reset the program by pressing the Reset button then you can add and
  run another operation.

## User-Interface
  ![image](https://github.com/MostafaElKaranshawy/Paint/assets/110842052/577e7aea-d950-4f74-b601-491a69f4c378)
  ![image](https://github.com/MostafaElKaranshawy/Paint/assets/110842052/09a6bc61-73e6-462d-b569-75ef5da9105a)


## Tutorial



https://github.com/MostafaElKaranshawy/Producer-Consumer/assets/110842052/e8964e91-202d-47c6-a2de-f7ca0add8137



## Contributeres
### Front-End
- [Mustafa ElKaranshawy](https://github.com/MostafaElKaranshawy)
- [Rowan Gamal](https://github.com/rowanxgamal)
### Back-End
- [Youssef Mahmoud](https://github.com/Youssef-Mahmoud0)
- [Youmna Yasser](https://github.com/yomnay888)
