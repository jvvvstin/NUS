import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Test3 {

  private static Member[] members;
  private static Team[] teams;

  private static void print(String s) {
    System.out.println("\n---- " + s + " ----");
  }

  private static Competition createCompetition() {
    members = new Member[] {
      new Member(12345678, "james@u.nus.edu"),
      new Member(34567890, "lucia@u.nus.edu"),
      new Member(56789012, "moana@u.nus.edu")
    };

    teams = new Team[] {
        new Team(101, 20, members[0]),
        new Team(256, 40, members[1]),
        new Team(888, -10, members[2])
    };

    Map<Integer, Team> map = new HashMap<>();
    for (Team t : teams) {
      map.put(t.getTeamID(), 
          new Team(t.getTeamID(), t.getPoints(), t.getMembers()));
    }

    Competition c = new Competition(map);
    return c;
  }

  public static void main(String[] args) {

    CS2030STest i = new CS2030STest();

    {
      Competition c = createCompetition();
      print("Test: Leader Board");
      System.out.println(c.leaderBoard());
    }

    print("Test: Update score of a team");
    {
      Competition c = createCompetition();
      c.score(101, 30);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.score(101, 30)",
          () -> c, new Competition(Map.of(
              101, new Team(101, 50, members[0]), 
              256, teams[1], 
              888, teams[2])));
    }

    print("Test: Penalize a team");
    {
      Competition c = createCompetition();
      c.penalize(256, 60);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.penalize(256, 60)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, new Team(256, -20, members[1]),
              888, teams[2])));
    }

    print("Test: Transfer points from one team to another");
    {
      Competition c = createCompetition();
      c.transfer(101, 888, 12);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.transfer(101, 888, 12)",
          () -> c, new Competition(Map.of(
              101, new Team(101, 8, members[0]),
              256, teams[1],
              888, new Team(888, 2, members[2])
              )));
    }

    print("Test: Can't transfer points if both teams have positive points");
    {
      Competition c = createCompetition();
      c.transfer(101, 256, 12);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.transfer(101, 256, 12)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1],
              888, teams[2]
              )));
    }

    print("Test: Can't transfer points from a team with negative points");
    {
      Competition c = createCompetition();
      c.transfer(888, 256, 12);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.transfer(888, 256, 12)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1],
              888, teams[2]
              )));
    }

    print("Test: Disqualifying a team");
    {
      Competition c = createCompetition();
      c.disqualify(888);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.disqualify(888)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1],
              888, new Team(888, -10, true, members[2]))));
    }

    print("Test: Leaderboard with disqualified team");
    {
      Competition c = createCompetition();
      c.disqualify(888);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.disqualify(888)\n"
          + "c.leaderBoard()",
          () -> c.leaderBoard(), "256 | lucia@u.nus.edu | 40.0\n" +
          "101 | james@u.nus.edu | 20.0\n");
    }

    print("Test: Calculate total points with disqualified team");
    {
      Competition c = createCompetition();
      c.disqualify(888);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.disqualify(888)\n"
          + "c.totalPoints()",
          () -> c.totalPoints(), 60.0);
    }

    print("Test: Merge two teams");
    {
      Competition c = createCompetition();
      c.mergeTeams(101, 888);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.mergeTeams(101, 888)",
          () -> c, new Competition(Map.of(
              101, new Team(101, 10, members[0], members[2]),
              256, teams[1]
              )));
    }

    print("Test: Can't merge if one of the team is disqualified");
    {
      Competition c = createCompetition();
      c.disqualify(888);
      c.mergeTeams(101, 888);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.disqualify(888)\n"
          + "c.mergeTeams(101, 888)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1],
              888, new Team(888, -10, true, members[2])
              )));
    }

    print("Test: Can't merge if one of the team is disqualified");
    {
      Competition c = createCompetition();
      c.disqualify(101);
      c.mergeTeams(101, 888);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.disqualify(101)\n"
          + "c.mergeTeams(101, 888)",
          () -> c, new Competition(Map.of(
              101, new Team(101, 20, true, members[0]),
              256, teams[1],
              888, teams[2]
              )));
    }

    print("Test: Scoring a non-existing team");
    {
      Competition c = createCompetition();
      c.score(593, 30);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.score(593, 30)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1], 
              888, teams[2])));
    }

    print("Test: Penalizing a non-existing team");
    {
      Competition c = createCompetition();
      c.penalize(593, 60);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.penalize(593, 60)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1],
              888, teams[2])));
    }

    print("Test: Transfering from a non-existing team");
    {
      Competition c = createCompetition();
      c.transfer(101, 593, 12);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.transfer(101, 593, 12)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1],
              888, teams[2]
              )));
    }

    print("Test: Transfering to a non-existing team");
    {
      Competition c = createCompetition();
      c.transfer(101, 503, 12);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.transfer(101, 593, 12)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1],
              888, teams[2]
              )));
    }

    print("Test: Disqualifying a non-existing team");
    {
      Competition c = createCompetition();
      c.disqualify(593);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.disqualify(593)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1],
              888, teams[2]
              )));
    }

    print("Test: Merging a non-existing team");
    {
      Competition c = createCompetition();
      c.mergeTeams(592, 593);
      i.expect(
          "Competition c = createCompetition();\n"
          + "c.mergeTeams(592, 593)",
          () -> c, new Competition(Map.of(
              101, teams[0],
              256, teams[1],
              888, teams[2]
              )));
    }
  }
}
