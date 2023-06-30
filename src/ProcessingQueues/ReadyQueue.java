package ProcessingQueues;
import PROCESS._Process_;

// This is a custom PriorityQueue used as ReadyQueue .
/*
 * Highest priority process stays at the top .
 * Each node in this queue contains 3 information - priority , process , next(link to next process(probably right next low priority process))
 * - functions provided -
 * new ReadyQueue()         - constructor
 * size()                   - retrieves size of current queue
 * peek()                   - retrieves top priority process
 * poll()                   - retrieves & removes top priority process
 * offer(process,priority)  - inserts a process according to its priority
 * EarliestProcess(time)    - retrieves process with min burst time with in the range of given time slot
 * remove(process)          - removes the process without changing the priority order of the rest processes
 */

public class ReadyQueue {
	private int size;
	private PQNode head;

	public ReadyQueue() {
		size = 0;
		head = null;
	}

	public int size() {
		return size;
	}

	public _Process_ peek() {
		return head.process;
	}

	public void remove(_Process_ minTime) {

		if (minTime.equals(head.process))
			head = head.next;
		else {
			PQNode temp = head;
			while (temp.next.process != minTime) {
				temp = temp.next;
			}
			temp.next = temp.next.next;
		}
		size--;
	}

	public _Process_ earliestNode(int clock) {
		PQNode current = head;
		PQNode min = null;
		boolean flag = false;
		while (current != null) {
			if (!flag && current.process.arrival_time <= clock) {
				min = current;
				flag = true;
			}
			if (flag && current.process.bursts.get(0).time < min.process.bursts.get(0).time && current.process.arrival_time <= clock)
				min = current;
			
			current = current.next;
		}
		if(min == null)
			return null;
		return min.process;
	}

	public void offer(_Process_ process, int priority) {
		PQNode tmp = new PQNode(process, priority);
		if ((size == 0) || (priority > head.priority)) {
			tmp.next = head;
			head = tmp;
		} else {
			PQNode p = head;
			PQNode q = null;
			while ((p != null) && (priority <= p.priority)) {
				q = p;
				p = p.next;
			}
			tmp.next = p;
			q.next = tmp;
		}
		size++;
	}

	public _Process_ poll() {
		PQNode node = head;
		head = head.next;
		size--;
		return node.process;
	}

}
