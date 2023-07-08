### A multi programming operating simulation program 
#### made to -
- #### ' track various characteristics of a process running in the cpu ' 
### Contents
- [overview](#overview--objective)
- [expectation](#expected-results)
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

### process creation
```
public void write(String filename) {
		Random rand = new Random();

		int cpu;
		int memor;
		int arri;
		int io;
		int memfree; // free some memory of the first memory requirement

		try {
			FileWriter fw = new FileWriter(filename);
			PrintWriter pw = new PrintWriter(fw);

			for (int i = 0; i < numberOfAllProcesses; i++) { // how many processes to generate
				memfree = 0;
				arri = InRange(rand, 1, 80);
				pw.print(arri);
				int inner = InRange(rand, 5, 9);
				for (int j = 0; j < inner; j++) {
					cpu = InRange(rand, 10, 100);
					memor = InRange(rand, 5, 200);
					io = InRange(rand, 20, 60);

					pw.print(" " + cpu + " " + memor + " ");
					pw.print(io);
					if (j != 0)
						memfree += memor;
				}
				cpu = InRange(rand, 10, 100);
				memor = InRange(rand, 0, memfree); // make a free or no change instruction in the memory
				memor = -1 * memor;
				pw.print(" " + cpu + " " + memor + " ");
				pw.println("-1");
			}
			pw.close();
			fw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
```


### write(String filename):

- This function writes randomly generated processes to a file.
- It starts by opening the file specified by the filename parameter using FileWriter and PrintWriter.
- It then enters a loop to generate the desired number of processes (numberOfAllProcesses).
- Inside the loop, it initializes variables to hold randomly generated values for CPU, memory, arrival time, I/O, and free memory.
- The arrival time is generated using the InRange() method.
- The number of bursts for each process is generated using the InRange() method to determine the value of inner.
- Another nested loop is used to generate the CPU, memory, and I/O values for each burst.
- The generated values are printed to the file using the PrintWriter.
- Additionally, a portion of the memory requirement is designated as free memory (memfree).
- The free memory value is subtracted from the memory requirement of the subsequent bursts to ensure the total memory usage does not exceed the initial requirement.
- After generating all the bursts for a process, a final burst is added with a negative memory value (memor) to represent a free or no change instruction in memory.
- The process creation loop continues until the desired number of processes is generated.
- Finally, the file is closed.
- Overall, 
  the [read()](https://github.com/souvik757/ProcessManagement/blob/bb5be52171da1b9f80ee30b41ca10ffb0cd99df3/src/DriverCode/INITIALIZE_SIMULATION.java) function reads job information from a file and creates _Process_ objects with bursts, while the write() function generates random processes and writes them to a file.