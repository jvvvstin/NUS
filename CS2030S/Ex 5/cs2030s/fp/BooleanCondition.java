/**
 * The Boolean Condition interface that can test
 * if a given condition is true or false.
 *
 * @author A0308249Y
 * @version CS2030S AY24/25 Semester 1
 */

package cs2030s.fp;

public interface BooleanCondition<T> {
  public boolean test(T condition);
} 
