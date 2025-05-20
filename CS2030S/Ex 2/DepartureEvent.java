/**
 * This class encapsulates a Departure event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */
class DepartureEvent extends Event {
  /**
   * The customer associated with this Departure event.
   * First customer has id 0. Next is 1, 2, etc.
   */
  private Customer customer;

  /**
   * Constructor for a Departure event.
   *
   * @param time        The time this event occurs.
   * @param customer    The customer associated with this
   *                    event.
   */
  public DepartureEvent(double time, Customer customer) {
    super(time);
    this.customer = customer;
  }

  /**
   * Returns the string representation of the Departure event.
   *
   * @return A string representing the Departure event.
   */
  @Override
  public String toString() {
    String str = String.format("%s: %s departed", super.toString(), 
        this.customer.toString());
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this Departure event.
   *
   * @return An empty array of events to be simulated.
   */
  @Override
  public Event[] simulate() {
    return new Event[] {};
  }
}
