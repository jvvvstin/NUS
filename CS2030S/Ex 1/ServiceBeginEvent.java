/**
 * This class encapsulates a Service Begin event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */
class ServiceBeginEvent extends Event {
  /**
   * The id of a customer associated with this Service Begin event.
   * First customer has id 0. Next is 1, 2, etc.
   */
  private int customerId;

  /**
   * The service time of the customer associated with
   * this Service Begin event. This field matters only
   * if the event type is ARRIVAL or SERVICE_BEGIN.
   */
  private double serviceTime;

  /**
   * The counter associated with this Service Begin event.
   * This field only matters if the event type is
   * SERVICE_BEGIN or SERVICE_END.
   */
  private Counter counter;

  /**
   * Constructor for a Service Begin event.
   *
   * @param time        The time this event occurs.
   * @param customerId  The customer associated with this Service Begin event.
   * @param serviceTime The time this customer takes for service.
   * @param counter     The counter associated with this Service Begin event.
   */
  public ServiceBeginEvent(double time, int customerId, double serviceTime, 
      Counter counter) {
    super(time);
    this.customerId = customerId;
    this.serviceTime = serviceTime;
    this.counter = counter;
  }
  
  /**
   * Returns the string representation of the Service Begin event.
   * 
   * @return A string representing the Service Begin event.
   */
  @Override
  public String toString() {
    String str = String.format("%s: Customer %d service begin (by Counter %s)", 
        super.toString(), this.customerId, this.counter.toString());
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this Service Begin event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    // updates the availability of the counter from true to false
    this.counter.updateAvailability();

    // calculates the end time of the service
    double endTime = this.getTime() + this.serviceTime;

    // trigger the next event which is a Service End event
    return new Event[] {
      new ServiceEndEvent(endTime, this.customerId, this.counter)
    };
  }
}
