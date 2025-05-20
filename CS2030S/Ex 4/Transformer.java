/**
 * The Transformer interface that can transform a type T
 * to type U.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */
public interface Transformer<T, U> {
  public U transform(T object);
}
