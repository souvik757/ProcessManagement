### A multi programming operating system simulation program 
#### made to -
- #### ' track various characteristics of a process running in the cpu ' 
### Contents
- [overview](#overview--objective)
- [expectation](#expected-results)
- [class designs](#class-designs)
- [process creation](#process-creation)
### overview & objective
- The project aims to develop a simulation of a multiprogramming operating system that emulates the behavior of a CPU scheduler and CPU execution. 
- The purpose of this simulation is to analyze and understand the performance characteristics of the system under different scenarios and workloads.
- The simulation will involve creating a virtual environment that mimics the key components of a multiprogramming operating system, including processes, CPU scheduling algorithms, and CPU execution. 
- The system will manage a set of processes that compete for CPU time, and the CPU scheduler will determine the order in which processes are allocated CPU resources.
### expected results
- At the end of the simulation, the project will generate a text file that captures the behavior of each process throughout its execution. 
- This file will provide a detailed record of each process's activity, including important events and timestamps. 
- The purpose of this output is to allow for further analysis and examination of individual process behavior.
- The generated text file may include the following information for each process :
```
 - PID
 - When it was loaded into the ready queue  
 - How many times it access the CPU            
 - Total time spent in the CPU         
 - Number of times it performed an IO  
 - Total time spent in performing IO   
 - Number of times it was waiting for memory 
 - Number of times its preempted             
 - Time it terminated or was killed          
 - Its final state: Killed ? Terminated     
```
### class designs

### process creation
