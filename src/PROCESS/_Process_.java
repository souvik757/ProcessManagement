package PROCESS;

import java.util.ArrayList;

// PROCESS.Job Class -> to keep trace of the information of a PROCESS.Job.
public class _Process_ {

	public ArrayList<burst> bursts; // all current bursts of the process

	// used in long-term function
	public int PID;
	public int load_ready; // When it was loaded into the ready queue.
	public int arrival_time;
	public int first_mem; // first memory requirement from long-term

	// used in short_term function
	public int cpu_count; // Number of times it was in the CPU.
	public int cpu_time; // Total time spent in the CPU
	public int io_count; // Number of times it performed an IO.
	public int io_time; // Total time spent in performing IO
	public int wait_count; // Number of times it was waiting for memory.
	public int preem_count; // Number of times its preempted (stopped execution because another process replaced it)
	public int termination_time;      // Time it terminated or was killed
	public State current_state;     // the processes will have one of the states: READY, WAITING, RUNNING, TERMINATED, KILLED.
	public State final_state; // Its final state: Killed or Terminated
	public int additional_memory; // additional memory requirement
	
	public _Process_() {  // original constructor
		this.load_ready = 0;
		
		this.PID = 0;
		this.arrival_time = 0;
		this.first_mem = 0;
		
		this.bursts = new ArrayList<>();
		
		this.cpu_count = 0;
		this.cpu_time = 0;
		this.io_count = 0;
		this.io_time = 0;
		this.wait_count = 0;
		this.preem_count = 0;
		this.termination_time = 0;
		this.current_state = null;
		this.final_state = null; 
		this.additional_memory = 0;
	}
	
	public _Process_(_Process_ process) {
		this.bursts = new ArrayList<>();
		for (int i = 0; i < process.bursts.size(); i++) {
			this.bursts.add(i, process.bursts.get(i));
		}

		this.load_ready = process.load_ready;

		this.PID = process.PID;
		this.arrival_time = process.arrival_time;
		this.first_mem = process.first_mem;


		this.cpu_count = process.cpu_count;
		this.cpu_time = process.cpu_time;
		this.io_count = process.io_count;
		this.io_time = process.io_time;
		this.wait_count = process.wait_count;
		this.preem_count = process.preem_count;
		this.termination_time = process.termination_time;
		this.current_state = process.current_state;
		this.final_state = process.final_state;
		this.additional_memory = process.additional_memory;
	}
     
}
