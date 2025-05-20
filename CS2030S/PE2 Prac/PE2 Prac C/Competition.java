import cs2030s.fp.Maybe;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Represents a competition between teams, with methods for scoring, 
 * penalizing, transferring points between teams, and combining teams. 
 * Also provides methods for printing the leaderboard and removing 
 * teams from the competition.
 */
public class Competition {

  private Map<Integer, Team> teams;

  /**
   * Constructs a new Competition object with the given teams.
   *
   * @param teams a map of team IDs to Team objects
   */
  public Competition(Map<Integer, Team> teams) {
    this.teams = teams;
  }

  /**
   * Returns a stream of all teams in the competition.
   *
   * @return a stream containing all accounts in the bank
   */
  private Stream<Team> getTeams() {
    return teams.values().stream();
  }


  /**
   * Returns the Team with the given ID, or null if no such team exists.
   *
   * @param id the ID of the team to find
   * @return the Team with the given ID, or null if no such team exists
   */
  private Team findTeam(int id) {
    // Map::get(id) return null if id does not exist.
    return teams.get(id);
  }

  /**
   * Calculates the total number of points earned by all teams in the
   * competition.  Teams with negative points are not included.
   *
   * @return total points earned by teams with positive points in the 
   * competition
   */
  public double totalPoints() {
    double sum = 0.0;
    for (Team team : teams.values()) {
      if (!team.isDisqualified()) {
        sum += team.getPoints();
      }
    }
    return sum;
  }

  /**
   * Transfers the given amount of points from one team to another.
   * The from team must have positive points and the to team must have
   * a negative points.  Otherwise, nothing happens.
   *
   * @param from the ID of the team to transfer points from
   * @param to the ID of the team to transfer points to
   * @param amount the number of points to transfer
   */
  public void transfer(int from, int to, double amount) {
    Team fromTeam = findTeam(from);
    Team toTeam = findTeam(to);

    if (fromTeam != null && toTeam != null && 
        fromTeam.getPoints() > 0 && toTeam.getPoints() < 0) {
      fromTeam.transferTo(toTeam, amount);
    }
  }

  /**
   * Adds the given amount of points to the team with the given ID.
   * If the given ID is invalid, nothing happen.
   *
   * @param id the ID of the team to score points for
   * @param points the number of points to add
   */
  public void score(int id, double points) {
    Team team = findTeam(id);
    if (team != null) {
      team.score(points);
    }
  }

  /**
   * Subtracts the given amount of points from the team with the given ID.
   * If the given ID is invalid, nothing happen.
   *
   * @param id the ID of the team to penalize
   * @param points the number of points to subtract
   */
  public void penalize(int id, double points) {
    Team team = findTeam(id);
    if (team != null) {
      team.penalize(points);
    }
  }

  /**
   * Combines two teams by merging their points and removing 
   * the original teams.
   *
   * @param a the ID of the first team to combine
   * @param b the ID of the second team to combine
   */
  public void mergeTeams(int a, int b) {
    Team teamA = findTeam(a);
    Team teamB = findTeam(b);

    if (teamA != null && teamB != null && !teamA.isDisqualified() && !teamB.isDisqualified()) {
      teamA.mergeWith(teamB);
      teams.remove(b);
    }
  }

  /**
   * Returns a string representing the leaderboard for the competition, 
   * sorted in ascending order of points earned.
   *
   * @return The leader board as a string
   */
  public String leaderBoard() {
    ArrayList<Team> sortedTeams = new ArrayList<>(teams.values());
    // Double.compare(d1,d2) returns 0 if d1 is numerically equal to d2; 
    // a value less than 0 if d1 is numerically less than d2; 
    // and a value greater than 0 if d1 is numerically greater than d2
    sortedTeams.sort((x, y) -> Double.compare(y.getPoints(), x.getPoints()));
    String s = "";
    for (Team team : sortedTeams) {
      if (!team.isDisqualified()) {
        s += team.getTeamID() + " | " + team + "\n";
      }
    }
    return s;
  }

  /**
   * Disqualify the team with the given ID from the competition.
   * If the given ID is invalid, nothing happens.
   *
   * @param id the ID of the team to remove
   */
  public void disqualify(int id) {
    Team team = findTeam(id);
    if (team != null) {
      team.disqualify();
    }
  }

  /**
   * Checks if this competition is equals to another competition, by comparing the teams.
   *
   * @param o the object to compare against
   * @return true if the two competitions are equal; false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Competition) {
      Competition c = (Competition) o;
      return c.teams.equals(this.teams);
    }
    return false;
  }

  /**
   * Return a string representation of the map of teams.
   *
   * @return a string representation of this competition.
   */
  @Override
  public String toString() {
    return this.teams.toString();
  }
}
