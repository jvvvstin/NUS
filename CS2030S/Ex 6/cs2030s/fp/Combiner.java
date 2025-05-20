package cs2030s.fp;

/**
 * The Combiner interface that can combine
 * two types S and T to type R.
 *
 * @author A0308249Y
 * @version CS2030S AY24/25 Semester 1
 */
@FunctionalInterface
public interface Combiner<S, T, R> {
  public abstract R combine(S obj1, T obj2);
}
