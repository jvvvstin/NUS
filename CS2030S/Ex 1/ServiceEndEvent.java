/**
 * This class encapsulates a Service End event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */
class ServiceEndEvent extends Event {
  /**
   * The id of a customer associated with this Service End event.
   * First customer has id  0. Next is 1, 2, etc. 
   */
  private int customerId;

  /**
   * The counter associated with this Service End event.
   * This field only matters if the event type is
   * SERVICE_BEGIN or SERVICE_END.
   */
  private Counter counter;

  /**
   * Constructor for a Service End event.
   *
   * @param time        The time this event occurs.
   * @param customerId  The customer associated with this Service End event.
   * @param counter     The counter associated with this Service End event.
   */
  public ServiceEndEvent(double time, int customerId, Counter counter) {
    super(time);
    this.customerId = customerId;
    this.counter = counter;
  }

  /**
   * Returns the string representation of the Service End event.
   *
   * @return A string representing the Service End event.
   */
  @Override
  public String toString() {
    String str = String.format("%s: Customer %d service done (by Counter %s)",
        super.toString(), this.customerId, this.counter.toString());
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

    // trigger the next event which is a Departure event
    return new Event[] {
      new DepartureEvent(this.getTime(), this.customerId)
    };
  }
}
