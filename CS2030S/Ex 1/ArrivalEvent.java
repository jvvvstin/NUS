/**
 * This class encapsulates an Arrival event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY 24/25 Semester 1
 */
class ArrivalEvent extends Event {
  /**
   * The id of a customer associated with this Arrival event.
   * First customer has id 0. Next is 1, 2, etc.
   */
  private int customerId;

  /**
   * The service time of the customer associated with
   * this Arrival event. This field matters only if 
   * the event type is ARRIVAL or SERVICE_BEGIN.
   */
  private double serviceTime;

  /**
   * The coffee shop associated with this Arrival event.
   * Contains an array of available counters to serve
   * the customers.
   */
  private CoffeeShop coffeeShop;

  /**
   * Constructor for an Arrival event.
   * 
   * @param arrivalTime The time this event occurs.
   * @param customerId  The customer associated with this
   *                    event.
   * @param serviceTime The time this customer takes
   *                    for service.
   * @param coffeeShop  The coffee shop associated with this
   *                    event.
   */
  public ArrivalEvent(double arrivalTime, int customerId, double serviceTime, 
      CoffeeShop coffeeShop) {
    super(arrivalTime);
    this.customerId = customerId;
    this.serviceTime = serviceTime;
    this.coffeeShop = coffeeShop;
  }

  /**
   * Returns the string representation of the Arrival event.
   * 
   * @return A string representing the Arrival event
   */
  @Override
  public String toString() {
    String str = String.format("%s: Customer %d arrives", super.toString(), this.customerId);
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this Arrival event.
   */
  @Override
  public Event[] simulate() {
    // get an instance of an available counter from the coffee shop
    Counter availCounter = this.coffeeShop.getAvailableCounter();

    // checks if there is an instance of a Counter returned
    // basically checks if there is an available counter
    if (availCounter == null) {

      // if there is no available counter, trigger the next event
      // which is a Departure event
      return new Event[] { 
        new DepartureEvent(this.getTime(), this.customerId)
      };
    }
    else {
      // else if there is an available counter, trigger the next
      // event which is a Service Begin event
      return new Event[] {
        new ServiceBeginEvent(this.getTime(), this.customerId, this.serviceTime, 
            availCounter)
      };
    }
  }
}
