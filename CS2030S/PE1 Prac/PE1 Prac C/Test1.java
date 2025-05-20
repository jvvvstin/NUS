import java.util.Arrays;

class Test1 {
  public static void main(String[] args) {
    CS2030STest we = new CS2030STest();

    we.expect("Passenger::toString returns the correct string",
        () -> new Passenger(1).toString(),
        "P0->COM1");

    we.expect("Passenger has the correct id",
        () -> new Passenger(4).toString(),
        "P1->COM4");

    we.expect("Passenger correctly returns its destination",
        () -> new Passenger(1).getDestination(), 
        1);
          
    we.expect("Passenger correctly checks if destination has been reached",
        () -> new Passenger(1).hasReachedDestination(1), 
        true);

    we.expect("Passenger correctly checks if destination has been reached",
        () -> new Passenger(1).hasReachedDestination(4), 
        false);
  }
}
