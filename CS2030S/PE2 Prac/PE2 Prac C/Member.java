/**
 * Represents a member of a team for competition with phone number 
 * and email address.  
 */
public class Member {

  private int phoneNumber;
  private String email;

  /**
   * Constructs a new member with the given phone number 
   * and email address.
   *
   * @param phoneNumber the phone number of the member
   * @param email       the email address of the member
   */
  public Member(int phoneNumber, String email) {
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  /**
   * Updates the phone number to the given value.
   *
   * @param phoneNumber the new phone number of the member
   */
  public Member updatePhoneNumber(int phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Changes the email address to the given value.
   *
   * @param email the new email address of the member
   */
  public Member updateEmail(String email) {
    this.email = email;
    return this;
  }

  /**
   * Returns the current email address of the member.
   *
   * @return the current email address 
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Returns the current phone number of the member.
   *
   * @return the phone number
   */
  public int getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * Returns a string representation of the member
   *
   * @return the member's email
   */
  @Override
  public String toString() {
    return this.email;
  }

  /**
   * Check if this member equals to a given one.
   *
   * @return true if this member equals to the given one; false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Member) {
      Member m = (Member) o;
      return (this.email.equals(m.email)) && (this.phoneNumber == m.phoneNumber);
    }
    return false;
  }
}
