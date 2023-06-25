// Contains information about a process and variables of its PCB(Process Control Block) .
// Contains variables required by a long term scheduler & short term scheduler to complete execution of a process .
import java.util.List ;
import java.util.ArrayList ;
public class Process {
    // current bursts of a process
    List<Burst> _bursts_ ;
    // - variables supposed to be used by long term scheduler -
    public int load_to_ready ;
    public int PID ;                   // -- takes as input
    public int ARRIVAL_TIME ;          // -- takes as input
    public int FIRST_MEMORY_ACCESS ;   // -- takes as input
    // - variables supposed to be used by short term scheduler -
    public ProcessState _current_state_ ;
    public ProcessState _final_state_   ;
    public int _cpu_count_ ;
    public int _cpu_time_  ;
    public int _io_count_  ;
    public int _io_time_   ;
    public int _wait_count_ ;
    public int _termination_time ;
    public int _preem_count_ ;
    public int _additional_memory_ ;
    public Process() {
        this._bursts_ = new ArrayList<>() ;

        this.load_to_ready = 0 ;

        this.PID = 0 ;
        this.ARRIVAL_TIME = 0 ;
        this.FIRST_MEMORY_ACCESS = 0 ;

        this._current_state_ = null;
        this._final_state_ = null;
        this._cpu_count_=0 ;
        this._cpu_time_=0  ;
        this._io_count_=0  ;
        this._io_time_=0   ;
        this._wait_count_=0 ;
        this._termination_time=0 ;
        this._preem_count_=0 ;
        this._additional_memory_=0 ;
    }
    public Process(Process p){
        this.load_to_ready       = p.load_to_ready ;
        for (int index = 0 ; index < p._bursts_.size() ; index ++)
            this._bursts_.add(index , p._bursts_.get(index)) ;

        this.PID                 = p.PID ;
        this.ARRIVAL_TIME        = p.ARRIVAL_TIME ;
        this.FIRST_MEMORY_ACCESS = p.FIRST_MEMORY_ACCESS ;

        this._current_state_     = p._current_state_ ;
        this._final_state_       = p._final_state_ ;
        this._cpu_count_         = p._cpu_count_ ;
        this._cpu_time_          = p._cpu_time_ ;
        this._io_count_          = p._io_count_ ;
        this._io_time_           = p._io_time_ ;
        this._wait_count_        = p._wait_count_ ;
        this._termination_time   = p._termination_time ;
        this._preem_count_       = p._preem_count_ ;
        this._additional_memory_ = p._additional_memory_ ;
    }
}
