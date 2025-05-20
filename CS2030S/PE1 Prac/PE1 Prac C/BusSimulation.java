// @author Axxxxxxx

import java.util.Scanner;

/**
 * This class implements a bus simulation.
 *
 * @author Ashish Dandekar
 * @version CS2030S AY23/24 Semester 2
 */ 
class BusSimulation extends Simulation {
  private Stop[] stops;
  private Bus[] buses;

  public static final double INTER_STOP_TRAVEL_TIME = 1.0;

  /** 
   * The list of passengner and bus events to populate
   * the simulation with.
   */
  public Event[] initEvents;

  /** 
   * Constructor for a bus simulation. 
   *
   * @param sc A scanner to read the parameters from.  
   */
  public BusSimulation(Scanner sc) {

    int numStops = sc.nextInt();
    int numBuses = sc.nextInt();
    int numPassengers = sc.nextInt();

    initEvents = new Event[numBuses + numPassengers];

    stops = new Stop[numStops];
    for (int i = 0; i < numStops; i++) {
      int capacity = sc.nextInt();
      stops[i] = new Stop(i, capacity);
    }

    int id = 0;

    buses = new Bus[numBuses];
    for (int i = 0; i < numBuses; i++) {
      double leaveTime = sc.nextDouble();
      int capacity = sc.nextInt();
      buses[i] = new Bus(capacity, numStops);
      // TODO
      // initEvents[id] = create new event for bus
      id += 1;
    }

    while (sc.hasNextDouble()) {
      double arrivalTime = sc.nextDouble();
      int source = sc.nextInt();
      int destination = sc.nextInt();
      // TODO
      // initEvents[id] = create new event for passenger
      id += 1;
    }
  }

  /**
   * Retrieve an array of events to populate the 
   * simulator with.
   *
   * @return An array of events for the simulator.
   */
  @Override
  public Event[] getInitialEvents() {
    return initEvents;
  }
}
