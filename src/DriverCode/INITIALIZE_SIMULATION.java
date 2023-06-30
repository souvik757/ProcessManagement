package DriverCode;

import PROCESS.State;
import PROCESS._Process_;
import PROCESS.burst;
import ProcessingQueues.PQNode;
import ProcessingQueues.Queue;
import ProcessingQueues.ReadyQueue;
import SortAlgorithm.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// The class that have the implementation of the Project
public class INITIALIZE_SIMULATION {

	public ReadyQueue readyQueue; // jobs that are ready to be running in the CPU.
	public Queue<_Process_> waitingIOQueue; // 1- waiting for I/O
	public Queue<_Process_> waitingMemQueue; // 2- waiting for memory
	public Queue<_Process_> jobQueue;
	public _Process_ runningJob; // the job that is running in the CPU
	public final int full_size = 1024;
	public int size85; // keep trace of how much used in RAM for 85% of the real size - MB
	public int size15; // 15% of the real size
	public int real_size; // size of the RAM without the OS usage
	public int clock;
	public List<_Process_> finalprocesses; // final jobs results.
	public int numberOfProcesses; // keep trace of the number of the processes in the system.
	public int numberOfAllProcesses; // number of the whole processes in the file .
	public double cpuUsage; // number of times of CPU usage in the system

	public INITIALIZE_SIMULATION(int os_intake, int numOfProcesses) {
		real_size = full_size - os_intake;
		readyQueue = new ReadyQueue();
		waitingIOQueue = new Queue<>();
		waitingMemQueue = new Queue<>();
		jobQueue = new Queue<>();
		runningJob = null;
		numberOfAllProcesses = numOfProcesses;
		numberOfProcesses = 0;
		clock = 0;
		size85 = 0;
		size15 = 0;
		cpuUsage = 0;
		finalprocesses = new ArrayList<>();
	}

	// Simulating the system (Main function), that have every thing on it.
	// the function that will be called in the Main.
	public void Simulation(INITIALIZE_SIMULATION sim) {
		String fn = "Write processes";
		sim.write(fn);
		sim.read(fn);

		sim.Short_Term();

		String ResultFile = "final results";
		sim.writeResults(ResultFile);
	}
	public double CPU_util() {
		return (cpuUsage / clock) * 100;
	}


	// 15% of the real size in the system.
	public double size_15() {
		return (real_size * 0.15);
	}

	// 85% of the real size in the system.
	public double size_85() {
		return (real_size * 0.85);
	}
	/*
     *** ShortTermSchedule() ->
    Here's a summary of what the program does, reflecting the role of a short-term scheduler:

     * - It checks for a deadlock condition in the system and handles it if detected.
     * - It periodically triggers a long-term scheduling algorithm to bring new processes into the ready queue.
     * - It implements the SRTF (Shortest Remaining Time First) scheduling algorithm, which selects the process with the shortest remaining burst time to execute next.
     * - It handles waiting IO processes, moving them to the ready queue when their IO operations are completed.
     * - It manages the waiting memory queue, ensuring that processes are moved to the ready queue only when sufficient memory is available.
     * - It handles the completion of CPU bursts for the running job, either by moving it to the waiting IO queue or marking it as terminated.
     * - It updates the CPU and IO times for the running job and waiting IO processes.
     * - It keeps track of system time (clock) and continues the scheduling process until all processes are completed.
     */
	public void Short_Term() {
		int i;
		int num;
		while (finalprocesses.size() != numberOfAllProcesses) { // do we finish all processes ?

			if (waitingMemQueue.size() == numberOfProcesses) // deadLock condition
				deadLock();

			if (clock % 200 == 0) { // wake-up call for long-term
				Long_Termfill();
			}
			// SRTF scheduling algorithm

			// 1) take off the earliest job from the ready queue and check if it has less
			// CPU time than runningJob.

			if (runningJob != null) { // surly there is a running PROCESS.Job in the CPU
				if (readyQueue.size() >= 1) {
					_Process_ checkJob = readyQueue.earliestNode(clock);
					if (checkJob != null) {
						if (checkJob.bursts.get(0).time < runningJob.bursts.get(0).time) {
							readyQueue.remove(checkJob);
							PQNode wasrunning = new PQNode(runningJob, runningJob.bursts.get(0).time);
							wasrunning.process.preem_count++;
							wasrunning.process.current_state = State.READY;
							readyQueue.offer(wasrunning.process, wasrunning.process.bursts.get(0).time);
							checkJob.cpu_count++;
							runningJob = checkJob;
							checkJob.current_state = State.RUNNING;
						}
					}
				}
			} else { // there is no running job at clock time
				if (readyQueue.size() >= 1) {
					_Process_ checkJob = readyQueue.earliestNode(clock);
					if (checkJob != null) {
						readyQueue.remove(checkJob);
						checkJob.cpu_count++;
						checkJob.current_state = State.RUNNING;
						runningJob = checkJob;
					}
				}
			}


			// 2) check if one of the waiting IO processes has done waiting to send to the ready queue.
			num = waitingIOQueue.size();
			for (i = 0; i < num; i++) {
				_Process_ io = waitingIOQueue.poll();
				int time = io.bursts.get(0).time;
				if (time == 0) { // has it finished its IO ? If yes, send it to the ready queue
					io.bursts.remove(0);
					int mem = io.bursts.get(0).cpu_instruction;
					if (mem <= 0) {
						size15 += mem; // will free some memory
						io.additional_memory += mem;
						readyQueue.offer(io, io.bursts.get(0).time);
					} else if (size15 + mem <= size_15()) {
						size15 += mem;
						io.additional_memory += mem;
						readyQueue.offer(io, io.bursts.get(0).time);
					} else
						waitingMemQueue.offer(io);
				} else {
					waitingIOQueue.offer(io);
				}
			}


			// 3) check the processes in waiting memory queue, if we have available space
			// (15% - additional memory requirement) or not,
			// if yes, we send it back to the ready queue and increment the size15, if no
			// it's still waiting in the waiting memory queue.
			num = waitingMemQueue.size();
			for (i = 0; i < num; i++) {
				_Process_ memJob = waitingMemQueue.poll();
				int mem = memJob.bursts.get(0).cpu_instruction;
				if (size15 + mem <= size_15()) {
					size15 += mem;
					memJob.additional_memory += mem;
					readyQueue.offer(memJob, memJob.bursts.get(0).time);
				} else {
					memJob.wait_count++;
					waitingMemQueue.offer(memJob);
				}
			}


			// 4) after finishing the CPU PROCESS.burst in the running job,
			// we check if it has other bursts ( IO .. CPU ... ) or it will be terminated
			// (-1) and decrement its memory from size (85%) & (15%).
			if (runningJob != null) {
				if (runningJob.bursts.get(0).time == 0) {
					if (runningJob.bursts.size() > 1) { // is there other bursts in the process ?
						runningJob.bursts.remove(0);
						_Process_ toIoJob = new _Process_(runningJob);
						toIoJob.io_count++;
						waitingIOQueue.offer(toIoJob);
					} else {
						runningJob.bursts.remove(0);
						_Process_ finishedJob = new _Process_(runningJob);
						finishedJob.final_state = State.TERMINATED;
						finishedJob.termination_time = clock;
						numberOfProcesses--;
						size85 -= finishedJob.first_mem;
						size15 -= finishedJob.additional_memory;
						finalprocesses.add(finishedJob);
					}
					runningJob = null;
				}
			}


			// processing the IO queue Jobs.
			num = waitingIOQueue.size();
			for (i = 0; i < num; i++) {
				_Process_ ioJob = waitingIOQueue.poll();
				ioJob.bursts.get(0).time--;
				ioJob.io_time++;
				waitingIOQueue.offer(ioJob);
			}


			// processing the running job
			if (runningJob != null) {
				cpuUsage++;
				runningJob.cpu_time++;
				runningJob.bursts.get(0).time--;
			}

			clock++;
		}
	}

	// Load the jobs to the RAM if there is a space
	public void Long_Termfill() {
		int num = jobQueue.size();
		for (int i = 0; i < num; i++) {
			_Process_ temp = jobQueue.poll();
			int mem = temp.bursts.get(0).cpu_instruction;
			if (size85 + mem <= size_85()) {
				size85 += mem;
				numberOfProcesses++;
				temp.first_mem = mem;
				temp.load_ready = clock;
				temp.current_state = State.READY;
				readyQueue.offer(temp, temp.bursts.get(0).time);
			} else
				jobQueue.offer(temp);
		}
	}
	/*
	 * The system should declare a deadlock and select the largest waiting process
	 * to be killed in order to get some free memory for the other processes.
	 */

	public void deadLock() {
		int num = waitingMemQueue.size();
		_Process_ max = null;
		boolean flag = false;
		if (num >= 1) {
			max = waitingMemQueue.peek();
			flag = true;
		}
		for (int i = 0; i < num; i++) {
			_Process_ job = waitingMemQueue.poll();
			if (job.bursts.get(0).cpu_instruction > max.bursts.get(0).cpu_instruction)
				max = job;
			waitingMemQueue.offer(job);

		}
		if (flag) {
			numberOfProcesses--;
			_Process_ maxJob = new _Process_(max);
			waitingMemQueue.remove(max);
			maxJob.termination_time = clock;
			maxJob.final_state = State.KILLED;
			size85 -= maxJob.first_mem;
			size15 -= maxJob.additional_memory;
			finalprocesses.add(maxJob);
		}
	}
	// CPU utilization in the system



	// Read the jobs from the file and load it into the job queue.
	public void read(String filename) {

		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);

			int indexburst;
			String line;
			int id = 1;
			_Process_[] sortJobs = new _Process_[numberOfAllProcesses];
			// this loop go through all processes
			for (int i = 0; i < sortJobs.length; i++) {
				indexburst = 0;
				// make a new PROCESS.Job
				_Process_ job = new _Process_();
				job.PID = id;
				sortJobs[i] = job;
				// this loop go through a single PROCESS.Job
				line = br.readLine();
				String info[] = line.split(" ");
				sortJobs[i].arrival_time = Integer.parseInt(info[0]);
				for (int j = 1; j < info.length - 1;) {
					// filling CPU information
					burst cpuBurst = new burst();
					cpuBurst.time = Integer.parseInt(info[j++]);
					cpuBurst.cpu_instruction = Integer.parseInt(info[j++]);
					cpuBurst.cpu = true;
					sortJobs[i].bursts.add(indexburst++, cpuBurst);

					if (!(j < info.length - 1))
						break;

					// filling IO information
					burst ioBurst = new burst();
					ioBurst.time = Integer.parseInt(info[j++]);
					ioBurst.cpu_instruction = -1;
					ioBurst.cpu = false;
					sortJobs[i].bursts.add(indexburst++, ioBurst);

				}
				id++;
			}

			// Sorting the jobs by arrival time
			MergeSort.sort(sortJobs , 0, sortJobs.length - 1) ;

			// filling the job queue
			for (int i = 0; i < sortJobs.length; i++) {
				jobQueue.offer(sortJobs[i]);
			}

			br.close();
			fr.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// write the processes that the user want on a file Randomly.
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

	// write the results of the simulation on a file.
	public void writeResults(String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			PrintWriter pw = new PrintWriter(fw);

			for (int i = 0; i < finalprocesses.size(); i++) {
				pw.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
				pw.println("Process: ");
				_Process_ currentJob = finalprocesses.get(i);
				pw.println("PID : " + currentJob.PID);
				pw.println("It was loaded into the ready queue  -> " + currentJob.load_ready);
				pw.println("It accessed the CPU for             -> " + currentJob.cpu_count);
				pw.println("Total time spent in the CPU         -> " + currentJob.cpu_time);
				pw.println("Number of times it performed an IO  -> " + currentJob.io_count);
				pw.println("Total time spent in performing IO   -> " + currentJob.io_time);
				pw.println("Number of times it was waiting for memory -> " + currentJob.wait_count);
				pw.println("Number of times its preempted             -> " + currentJob.preem_count);
				pw.println("Time it terminated or was killed          -> " + currentJob.termination_time);
				pw.println("Its final state: Killed or Terminated     -> " + currentJob.final_state);
				pw.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");

			}
			pw.println("CPU Utilization -> " + CPU_util() + " %");
			pw.close();
			fw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// give a random number between the range of min and max.
	protected int InRange(Random random, int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}
}