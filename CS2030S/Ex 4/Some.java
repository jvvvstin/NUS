/**
 * This class implements Some container.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */ 
public class Some<T> {
  /**
   * The content that is to be stored in Some container.
   */
  private final T content;

  /**
   * Constructor for Some container.
   *
   * @param content The content to be stored in Some container.
   */
  private Some(T content) {
    this.content = content;
  }

  /**
   * Factory method for a Some object.
   *
   * @param content The content to be stored in Some container.
   * @return Some container storing the content.
   */
  public static <T> Some<T> some(T content) {
    return new Some<T>(content);
  }

  /**
   * Maps Some container to another Some container.
   *
   * @param transformer Transformer that transforms Some container storing T to
   *                    Some container storing U.
   * @return Some container that now stores a content of type U.
   */
  public <U> Some<U> map(Transformer<? super T, ? extends U> transformer) {
    return new Some<U>(transformer.transform(this.content));
  }

  /**
   * Checks if two Some containers have the same content value.
   *
   * @param object  Some container that will be checked against the current 
   *                Some container.
   * @return A boolean value representing whether the two Some containers have
   *         the same content values.
   */
  @Override
  public boolean equals(Object object) {
    Some<?> container = null;

    // checks if the object is the same instance
    if (this == object) {
      return true;
    }

    // checks if the object is an instance of Some unknown type
    if (object instanceof Some<?>) {

      // cast the object to Some unknown type
      container = (Some<?>) object;

      // checks if the Some container contains the same content value as the 
      // current Some container
      return this.content.equals(container.content);
    }
    return false;
  }

  /**
   * Returns the string representation of the Some container.
   *
   * @return A string representing the Some container.
   */
  @Override
  public String toString() {
    String str = String.format("[%s]", content);
    return str;
  }
}
