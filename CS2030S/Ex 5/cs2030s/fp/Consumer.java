/**
 * The Consumer interface that can consume a value
 * of type T.
 *
 * @author A0308249Y
 * @version CS2030S AY24/25 Semester 1
 */

package cs2030s.fp;

public interface Consumer<T> {
  public void consume(T val);
}
