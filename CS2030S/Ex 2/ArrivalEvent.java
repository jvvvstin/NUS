/**
 * This class encapsulates an Arrival event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY 24/25 Semester 1
 */
class ArrivalEvent extends Event {
  /**
   * The customer associated with this Arrival event.
   * First customer has id 0. Next is 1, 2, etc.
   */
  private Customer customer;

  /**
   * The coffee shop associated with this Arrival event.
   * Contains an array of available counters to serve
   * the customers.
   */
  private CoffeeShop coffeeShop;

  /**
   * The coffee shop queue associated with this Arrival event.
   * Contains an array of objects (Customers) that are in the queue.
   */
  private Queue coffeeShopQueue;

  /**
   * Constructor for an Arrival event.
   * 
   * @param arrivalTime     The time this event occurs.
   * @param customer        The customer associated with this
   *                        event.
   * @param coffeeShop      The coffee shop associated with this
   *                        event.
   * @param coffeeShopQueue The coffee shop queue associated with
   *                        this event.
   */
  public ArrivalEvent(double arrivalTime, Customer customer, 
      CoffeeShop coffeeShop, Queue coffeeShopQueue) {
    super(arrivalTime);
    this.customer = customer;
    this.coffeeShop = coffeeShop;
    this.coffeeShopQueue = coffeeShopQueue;
  }

  /**
   * Returns the string representation of the Arrival event.
   * 
   * @return A string representing the Arrival event
   */
  @Override
  public String toString() {
    String str = String.format("%s: %s arrives %s", super.toString(), 
        this.customer.toString(), this.coffeeShopQueue.toString());
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

      // if there is no available counter, check if the coffee shop queue is
      // full
      if (this.coffeeShopQueue.isFull()) {
        // if the coffee shop queue is full, trigger the next event, 
        // DepartureEvent
        return new Event[] {
          new DepartureEvent(this.getTime(), this.customer)
        };
      } else {
        // else (if it is not full), trigger the next event, which is WaitEvent
        return new Event[] {
          new WaitEvent(this.getTime(), this.customer, this.coffeeShopQueue)
        };
      }
    } else {
      // else if there is an available counter, trigger the next
      // event which is a Service Begin event
      return new Event[] {
        new ServiceBeginEvent(this.getTime(), this.customer, availCounter, 
            this.coffeeShopQueue)
      };
    }
  }
}
