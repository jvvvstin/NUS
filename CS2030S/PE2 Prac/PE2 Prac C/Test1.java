/**
 * Test 1.
 */
class Test1 {
  private static void print(String s) {
    System.out.println("\n---- " + s + " ----");
  }

  /**
   * Main method for Test1.
   *
   * @param args Ignored and unused command line arguments.
   */
  public static void main(String[] args) {

    CS2030STest i = new CS2030STest();

    print("Test: Get the email address and phone number from a member");
    {
      Member m1 = new Member(12345678, "han@millenniumfalcon.com");
      i.expectReturn(
          "Member m = new Member(12345678, \"han@millenniumfalcon.com\");\n"
          + "m.getEmail();",
          () -> m1.getEmail(), "han@millenniumfalcon.com");
      i.expectReturn(
          "m.getPhoneNumber();",
          () -> m1.getPhoneNumber(), 12345678);
    }

    print("Test: Update the phone number of a member");
    {
      Member m1 = new Member(12345678, "han@millenniumfalcon.com");
      Member m2 = m1.updatePhoneNumber(87654321);
      i.expectReturn(
          "Member m1 = new Member(12345678, \"han@millenniumfalcon.com\");\n"
          + "Member m2 = m1.updatePhoneNumber(87654321);\n"
          + "m1.getPhoneNumber();",
          () -> m1.getPhoneNumber(), 12345678);
      i.expectReturn(
          "m2.getPhoneNumber();",
          () -> m2.getPhoneNumber(), 87654321);
    }

    print("Test: Update the email address of a member");
    {
      Member m1 = new Member(12345678, "han@millenniumfalcon.com");
      Member m2 = m1.updateEmail("chewie@millenniumfalcon.com");
      i.expectReturn(
          "Member m1 = new Member(12345678, \"han@millenniumfalcon.com\");\n"
          + "Member m2 = m1.changeEmail(\"chewie@millenniumfalcon.com\")\n"
          + "m1.getEmail();",
          () -> m1.getEmail(), "han@millenniumfalcon.com");
      i.expectReturn(
          "m2.getEmail();",
          () -> m2.getEmail(), "chewie@millenniumfalcon.com");
    }
  }
}
