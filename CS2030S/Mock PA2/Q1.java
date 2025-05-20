/**
 * @author A0308249Y
 */
import cs2030s.fp.Maybe;
import java.util.stream.Stream;

public class Q1 {
  public static long collatz(int n) {
    return Stream.iterate(n, x -> Maybe.of(x)
                                       .filter(y -> y % 2 == 0)
                                       .map(y -> y / 2)
                                       .orElse(() -> 3 * x + 1))
                 .takeWhile(x -> x != 1)
                 .count();
  }
}
