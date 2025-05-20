class Customer {
  /**
   * The id of a customer.
   * First customer has id 0. Next is 1, 2, etc.
   */
  private final int customerId;

  /**
   * The service time of the customer associated with
   * the respective event. This field matters only if
   * the event type is ARRIVAL or SERVICE_BEGIN.
   */
  private double serviceTime;

  /**
   * The last customerId instantiated for a customer.
   */
  private static int lastCustomerId = 0;

  /**
   * The Coffee that the Customer wishes to order.
   */
  private Order order;

  /**
   * Constructor for a Customer.
   *
   * @param serviceTime The time this customer takes
   *                    for service.
   * @param coffeeId    The id of the Coffee drink
   *                    that the customer wishes to
   *                    order.
   * @param instruction The instruction for the Order
   */
  public Customer(double serviceTime, int coffeeId, String instruction) {
    this.customerId = this.lastCustomerId;
    this.serviceTime = serviceTime;
    this.lastCustomerId++;
    this.order = Order.fromInt(coffeeId, instruction);
  }
  
  /**
   * Returns the end time of the service.
   *
   * @return A double representing the end time of the service event.
   */
  public double calculateEndTime(double time) {
    return time + this.serviceTime;
  }
  
  /**
   * Returns the Coffee drink of the customer.
   *
   * @return A string representing the Drink.
   */
  public String getCoffeeDrink() {
    return this.order.toString();
  }

  /**
   * Returns the string representation of the Customer.
   *
   * @return A string representing the Customer class.
   */
  @Override
  public String toString() {
    String str = String.format("C%d", this.customerId);
    return str;
  }
}
