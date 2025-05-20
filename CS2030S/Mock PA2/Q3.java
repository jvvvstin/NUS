/**
 * @author A0308249Y
 */
import cs2030s.fp.Maybe;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

public class Q3 {
  public static Maybe<String> longestOdd(List<Bus> buses) {
    return Maybe.of(buses.stream()
                         .flatMap(x -> x.getStops().stream())
                         .filter(x -> x.getNumber() % 2 == 1)
                         .sorted((x, y) -> y.getName().length() - x.getName().length())
                         .limit(1)
                         .map(x -> x.getName())
                         .reduce(null, (x, y) -> y));
  }
}
