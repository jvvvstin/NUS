/**
 * This class encapsulates a Service End event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */
class ServiceEndEvent extends Event {
  /**
   * The customer associated with this Service End event.
   * First customer has id 0. Next is 1, 2 etc.
   */
  private Customer customer;

  /**
   * The counter associated with this Service End event.
   * This field only matters if the event type is
   * SERVICE_BEGIN or SERVICE_END.
   */
  private Counter counter;
  
  /**
   * The coffee shop queue associated with this Service End event.
   * Contains an array of objects (Customers) that are in the queue.
   */
  private Queue coffeeShopQueue;

  /**
   * Constructor for a Service End event.
   *
   * @param time            The time this event occurs.
   * @param customer        The customer associated with this Service End event.
   * @param counter         The counter associated with this Service End event.
   * @param coffeeShopQueue The coffee shop queue associated with this Service 
   *                        End event.
   */
  public ServiceEndEvent(double time, Customer customer, Counter counter, 
      Queue coffeeShopQueue) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.coffeeShopQueue = coffeeShopQueue;
  }

  /**
   * Returns the string representation of the Service End event.
   *
   * @return A string representing the Service End event.
   */
  @Override
  public String toString() {
    String str = String.format("%s: %s served %s (by %s)",
        super.toString(), this.customer.toString(), 
        this.customer.getCoffeeDrink(), this.counter.toString());
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this Service End event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    // updates the availability of the counter from false to true
    this.counter.updateAvailability();

    // checks if the coffee shop queue is not empty
    if (!this.coffeeShopQueue.isEmpty()) {
      // if queue is not empty, retrieve the first Customer in the queue
      Customer c = (Customer) this.coffeeShopQueue.deq();

      // trigger the next sequence of events
      return new Event[] {
        // Departure of original Customer
        new DepartureEvent(this.getTime(), this.customer),
        // Service Begin of the new customer that was previously first in the 
        // queue
        new ServiceBeginEvent(this.getTime(), c, this.counter, 
            this.coffeeShopQueue)
      };
    } else {
      // else if the queue is empty (i.e. no one is in the queue)
      // trigger the next event which is a Departure event of the original
      // Customer
      return new Event[] {
        new DepartureEvent(this.getTime(), this.customer)
      };
    }
  }
}
