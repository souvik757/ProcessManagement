import DriverCode.INITIALIZE_SIMULATION;

// initialize the OS :::
public class Main {
    public static void main(String[] args) {
        INITIALIZE_SIMULATION simulation = new INITIALIZE_SIMULATION(452 , 500) ;
        simulation.Simulation(simulation);
    }
}