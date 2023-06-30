package ProcessingQueues;

import PROCESS._Process_;

public class PQNode {
    public _Process_ process;
    public int priority;
    public PQNode next;

    public PQNode() {
        next = null;
    }

    public PQNode(_Process_ process, int priority) {
        this.process = process ;
        this.priority = priority ;
    }
}
