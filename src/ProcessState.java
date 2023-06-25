//  -- This enum stores a list of all valid states a process can achieve --

public enum ProcessState {
    _ready_       , // ready   state
    _waiting_io_  , // waiting state
    _waiting_cpu_ , // . . . .
    _running_     , // running state
    _terminated_  , // aborted state
    _killed_        // . . . .
}