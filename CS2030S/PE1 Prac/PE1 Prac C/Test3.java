class Test3 {
  public static void main(String[] args) {
    CS2030STest we = new CS2030STest();

    Stop s = new Stop(1, 2);
    Passenger p1 = new Passenger(4);
    Passenger p2 = new Passenger(2);
    Passenger p3 = new Passenger(0);

    we.expect("Stop::toString returns the correct string",
        () -> s.toString().trim(),
        "COM1 [ ]");

    we.expect("Stop correctly accepts passenger if queue is not full",
        () -> {
          return s.addPassenger(p1);
        },
        true);

    we.expect("Stop correctly accepts passenger if queue is not full",
        () -> {
          return s.addPassenger(p2);
        },
        true);

    we.expect("Stop::toString returns the corret string",
        () -> {
          return s.toString().trim();
        },
        "COM1 [ P0->COM4 P1->COM2 ]");

    we.expect("Stop correctly rejects passenger if queue is full",
        () -> {
          return s.addPassenger(new Passenger(0));
        },
        false);

    we.expect("Stop correctly removes passenger",
        () -> {
          return s.removePassenger().toString();
        },
        "P0->COM4");

    we.expect("Stop correctly removes another passenger",
        () -> {
          return s.removePassenger().toString();
        },
        "P1->COM2");

    we.expect("Stop correctly returns null when queue is empty",
        () -> {
          return s.removePassenger();
        },
        null);
  }
}
