### Process managing Queue 
```
- The Operating system manages various types of queues for each of the process states. 
- The PCB related to the process is also stored in the queue of the same state. 
- If the PROCESS._Process_ is moved from one state to another state then its PCB is also unlinked from the corresponding queue and 
  added to the other state queue in which the transition is made.
```
<img src="ProcessQueues.PNG"  width="360" height="360" />

### Job Queue
```
- In starting, all the processes get stored in the job queue.
- It is maintained in the secondary memory. 
- The long term scheduler (Job scheduler) picks some of the jobs and put them in the primary memory.
```
### Ready Queue
````
- Ready queue is maintained in primary memory.
- The short term scheduler picks the job from the ready queue and dispatch to the CPU for the execution.
````
### Waiting Queue
````
- When the process needs some IO operation in order to complete its execution,
  OS changes the state of the process from running to waiting.
- The context (PCB) associated with the process gets stored on the waiting queue ,
  which will be used by the Processor when the process finishes the IO.
````