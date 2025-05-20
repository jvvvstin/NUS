import java.util.List;

/**
 * Test 2.
 */
class Test2 {
  private static void print(String s) {
    System.out.println("\n---- " + s + " ----");
  }

  /**
   * Main method for Test2.
   *
   * @param args Ignored and unused command line arguments.
   */
  public static void main(String[] args) {

    CS2030STest i = new CS2030STest();

    Member m1 = new Member(12345678, "ahkow@u.nus.edu");

    print("Test: Getting the team ID and points");
    {
      Team t = new Team(5, 100.0, m1);
      i.expectReturn(
          "Member m = new Member(12345678, \"ahkow@u.nus.edu\");\n"
          + "Team t = new Team(5, 100.0, m);\n"
          + "t.getTeamID();",
          () -> t.getTeamID(), 5);
      i.expectReturn(
          "t.getPoints();",
          () -> t.getPoints(), 100.0);
    }

    print("Test: Adding points to a team");
    {
      Team t1 = new Team(5, 100.0, m1);
      Team t2 = t1.score(50.0);
      i.expectReturn(
          "Member m = new Member(12345678, \"ahkow@u.nus.edu\");\n"
          + "Team t1 = new Team(5, 100.0, m);\n"
          + "Team t2 = t1.score(50.0);\n"
          + "t1.getPoints();",
          () -> t1.getPoints(), 100.0);
      i.expectReturn(
          "t2.getPoints();",
          () -> t2.getPoints(), 150.0);
    }

    print("Test: Penalize a team");
    {
      Team t1 = new Team(5, 100.0, m1);
      Team t2 = t1.penalize(180.0);
      i.expectReturn(
          "Member m = new Member(12345678, \"ahkow@u.nus.edu\");\n"
          + "Team t1 = new Team(5, 100.0, m);\n"
          + "Team t2 = t1.penalize(180.0);\n"
          + "t1.getPoints();",
          () -> t1.getPoints(), 100.0);
      i.expectReturn(
          "t2.getPoints();",
          () -> t2.getPoints(), -80.0);
    }

    print("Test: Transfer points to another team");
    {
      Team t1 = new Team(5, 100.0, m1);
      Team t2 = new Team(6, -80.0, m1);
      Pair<Team, Team> pair = t1.transferTo(t2, 40.0);
      i.expectReturn(
          "Member m = new Member(12345678, \"ahkow@u.nus.edu\");\n"
          + "Team t1 = new Team(5, 100.0, m);\n"
          + "Team t2 = new Team(6, -80.0, m);\n"
          + "Pair<Team, Team> pair = t1.transferTo(t2, 40.0);\n"
          + "t1.getPoints();",
          () -> t1.getPoints(), 100.0);
      i.expectReturn(
          "t2.getPoints();",
          () -> t2.getPoints(), -80.0);
      i.expectReturn(
          "pair.getFirst().getPoints();",
          () -> pair.getFirst().getPoints(), 60.0);
      i.expectReturn(
          "pair.getSecond().getPoints();",
          () -> pair.getSecond().getPoints(), -40.0);
    }

    print("Test: Merging two teams");
    {
      Member m2 = new Member(23456789, "ahhua@u.nus.edu");
      Team t1 = new Team(5, 100.0, m1);
      Team t2 = new Team(6, 200.0, m2);
      Team t3 = t1.mergeWith(t2);
      i.expectReturn(
          "Member m = new Member(12345678, \"ahkow@u.nus.edu\");\n"
          + "Member m2 = new Member(23456789, \"ahhua@u.nus.edu\");\n"
          + "Team t1 = new Team(5, 100.0, m);\n"
          + "Team t2 = new Team(6, 200.0, m2);\n"
          + "Team t3 = t1.mergeWith(t2);\n"
          + "t1.getPoints();",
          () -> t1.getPoints(), 100.0);
      i.expectReturn(
          "t1.getMembers();",
          () -> List.of(t1.getMembers()), List.of(m1));
      i.expectReturn(
          "t3.getPoints();",
          () -> t3.getPoints(), 300.0);
      i.expectReturn(
          "t3.getMembers();",
          () -> List.of(t3.getMembers()), List.of(m1, m2));
    }
  }
}
