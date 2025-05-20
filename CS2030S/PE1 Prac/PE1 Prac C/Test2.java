class Test2 {
  public static void main(String[] args) {
    CS2030STest we = new CS2030STest();

    Bus b = new Bus(2, 3);
    we.expect("Bus::toString returns the correct string",
        () -> b.toString().trim(),
        "B0@COM0 passengers: [ ] [ ] [ ]");

    we.expect("Bus correctly boards a passenger going to COM2",
        () -> {
          try {
            return b.board(new Passenger(2)).toString().trim();
          } catch (CannotBoardException e) {
            return "";
          }
        },
        "B0@COM0 passengers: [ ] [ ] [ P0->COM2 ]");

    we.expect("Bus correctly boards a passenger going to COM1",
        () -> {
          try {
            return b.board(new Passenger(1)).toString().trim();
          } catch (CannotBoardException e) {
            return "";
          }
        },
        "B0@COM0 passengers: [ ] [ P1->COM1 ] [ P0->COM2 ]");

    we.expectCheckedException("Bus throws CannotBoardException when bus is full",
        () -> b.board(new Passenger(1)),
        new CannotBoardException(""));

    we.expect("Bus throws CannotBoardException with the correct msg when bus is full",
        () -> {
          try {
            b.board(new Passenger(1));
          } catch (CannotBoardException e) {
            return e.getMessage();
          }
          return "";
        },
        "Bus is full");

    we.expect("Bus correctly moves to COM1",
        () -> b.move().toString().trim(),
        "B0@COM1 passengers: [ ] [ P1->COM1 ] [ P0->COM2 ]");

    we.expect("Bus correctly alights passengers at COM1",
        () -> b.alight().toString().trim(),
        "B0@COM1 passengers: [ ] [ ] [ P0->COM2 ]");

    we.expect("Bus correctly moves to COM2",
        () -> b.move().toString().trim(),
        "B0@COM2 passengers: [ ] [ ] [ P0->COM2 ]");

    we.expect("Bus correctly alights passengers at COM2",
        () -> b.alight().toString().trim(),
        "B0@COM2 passengers: [ ] [ ] [ ]");

    we.expect("Bus correctly moves back to COM0",
        () -> b.move().toString().trim(),
        "B0@COM0 passengers: [ ] [ ] [ ]");

    we.expect("new Bus correctly updates its id",
        () -> new Bus(1, 1).toString().trim(),
        "B1@COM0 passengers: [ ]");
  }
}
