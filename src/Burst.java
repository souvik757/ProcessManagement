public class Burst { // keeps information of the burst of a process .
    // - Burst time is the time duration ,
    // a process spends utilizing the cpu for computation or executing its tasks
    // , to be precise it is called CPU burst (_cpu_ = true)
    // - An IO burst is IO wait or IO operation ,(_cpu_ = false)
    // it occurs when a process needs to wait for input output operations to be completed .
    public int _time_ ;
    public boolean _cpu_ ; // true -> cpu burst , false -> I/O burst
    public int _cpu_instruction_ ;
}
