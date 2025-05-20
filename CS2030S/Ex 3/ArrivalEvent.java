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
   * Constructor for an Arrival event.
   * 
   * @param arrivalTime     The time this event occurs.
   * @param customer        The customer associated with this
   *                        event.
   * @param coffeeShop      The coffee shop associated with this
   *                        event.
   */
  public ArrivalEvent(double arrivalTime, Customer customer, 
      CoffeeShop coffeeShop) {
    super(arrivalTime);
    this.customer = customer;
    this.coffeeShop = coffeeShop;
  }

  /**
   * Returns the string representation of the Arrival event.
   * 
   * @return A string representing the Arrival event
   */
  @Override
  public String toString() {
    String str = String.format("%s: %s arrives %s", super.toString(), 
        this.customer.toString(), this.coffeeShop.displayQueue());
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this Arrival event.
   */
  @Override
  public Event[] simulate() {
    // get an instance of an available Counter from the Coffee Shop
    Counter availCounter = this.coffeeShop.getAvailableCounter();

    // checks if there is an instance of a Counter returned
    // basically checks if there is an available Counter
    if (availCounter == null) {
      // if there are no available Counters,
      // checks if all the Queues for all the Counters are full
      boolean countersQueuesFull = this.coffeeShop.areCountersQueuesFull();
      if (countersQueuesFull) {
         
        // if all the Counter Queues are full, check if the Coffee Shop Queue is
        // full
        if (this.coffeeShop.isQueueFull()) {
          // if the coffee shop queue is full, trigger the next event, 
          // DepartureEvent
          return new Event[] {
            new DepartureEvent(this.getTime(), this.customer)
          };
        } else {
          // else (if the entrance Queue is not full), trigger the next event, 
          // which is JoinCoffeeShopQueueEvent
          return new Event[] {
            new JoinCoffeeShopQueueEvent(this.getTime(), this.customer, 
                this.coffeeShop)
          };
        }
      } else {
        // else (if the Counter Queues are not full (there exists one Counter 
        // Queue that is not full), trigger the next event, 
        // JoinCounterQueueEvent
        return new Event[] {
          new JoinCounterQueueEvent(this.getTime(), this.customer, 
              this.coffeeShop)
        };
      }

    } else {
      // else if there is an available Counter, trigger the next
      // event which is a Service Begin event
      return new Event[] {
        new ServiceBeginEvent(this.getTime(), this.customer, availCounter, 
            this.coffeeShop)
      };
    }
  }
}
