import java.util.Arrays;

/**
 * Represents a team in a competition, with members, ID and points.
 */
public class Team {

  private Member[] members;
  private int teamID;
  private double points;
  private boolean isDisqualified;

  /**
   * Constructs a new Team object with the given ID, points, and members.
   *
   * @param teamID      the ID of the team
   * @param points      the initial number of points for the team
   * @param members the members of the team
   */
  public Team(int teamID, double points, Member... members) {
    this.teamID = teamID;
    this.points = points;
    this.members = members;
    this.isDisqualified = false;
  }

  /**
   * Constructs a new Team object with the fields of a given team, but with 
   * the given disqualified flag.
   *
   * @param teamID      the ID of the team
   * @param points      the initial number of points for the team
   * @param isDisqualified whether this team is disqualified
   * @param members     the members of the team
   */
  public Team(int teamID, double points, boolean isDisqualified, Member... members) {
    this.teamID = teamID;
    this.points = points;
    this.members = members;
    this.isDisqualified = isDisqualified;
  }

  /**
   * Returns the ID of the team.
   *
   * @return the ID of the team
   */
  public int getTeamID() {
    return this.teamID;
  }

  /**
   * Returns the number of points earned by the team.
   *
   * @return the number of points earned by the team
   */
  public double getPoints() {
    return this.points;
  }

  /**
   * Returns the members of the team.
   *
   * @return An array containing the members of the team
   */
  public Member[] getMembers() {
    return this.members;
  }

  /**
   * Adds the given number of points to the team's score.
   *
   * @param points the number of points to add to the team's score
   */
  public Team score(double points) {
    this.points += points;
    return this;
  }

  /**
   * Subtracts the given number of points from the team's score.
   *
   * @param points the number of points to subtract from the team's score
   */
  public Team penalize(double points) {
    this.points -= points;
    return this;
  }

  /**
   * Transfers the given number of points from this team to the given recipient team.
   *
   * @param toTeam the recipient team to transfer points to
   * @param points the number of points to transfer
   */
  public Pair<Team, Team> transferTo(Team toTeam, double points) {
    this.penalize(points);
    toTeam.score(points);
    return new Pair<>(this, toTeam);
  }

  /**
   * Merges this team with the given team by combining their scores and members.
   *
   * @param other the team to merge with
   * @return a new Team with the combined points and list of team members
   */
  public Team mergeWith(Team other) {
    this.points += other.points;
    this.members = concat(this.members, other.members);
    return this;
  }

  /**
   * Concatenates two arrays of Member objects into a new array.
   *
   * @param a the first array of Members
   * @param b the second array of Members
   * @return a new array containing all the elements of a followed by all the elements of b
   */
  private Member[] concat(Member[] arr1, Member[] arr2) {
    int len1 = arr1.length;
    int len2 = arr2.length;

    Member[] c = new Member[len1 + len2];
    System.arraycopy(arr1, 0, c, 0, len1);
    System.arraycopy(arr2, 0, c, len1, len2);
    return c;
  }

  /**
   * Mark this team as disqualified.
   */
  public Team disqualify() {
    this.isDisqualified = true;
    return this;
  }

  /**
   * Check if this team is disqualified.
   */
  public boolean isDisqualified() {
    return this.isDisqualified;
  }

  /**
   * Returns a string representation of the team, including the team's members, ID, and points.
   *
   * @return a string representation of the team
   */
  @Override
  public String toString() {
    String returnString = "";
    for (int i = 0; i < this.members.length - 1; i++) {
      returnString += this.members[i] + ", ";
    }
    returnString += this.members[this.members.length - 1];
    returnString += " | " + this.points;
    if (this.isDisqualified) {
      returnString += " [disqualified]";
    }
    return returnString;
  }

  /**
   * Checks if this object equals to another
   *
   * @return true if the given object o equals to this.  Otherwise return false
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Team) {
      Team t = (Team) o;
      return t.teamID == this.teamID && t.points == this.points && 
        // Compare if the elements of the two arrays are equals.
        Arrays.equals(t.members, this.members);
    }
    return false;
  }
}
