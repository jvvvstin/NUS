package cs2030s.fp;

/**
 * This class implements a lazily evaluated value.
 * The value is computed only when needed and the
 * value is not recomputed.
 *
 * @author A0308249Y
 * @version CS2030S AY24/25 Semester 1
 */

public class Lazy<T> {
  /**
   * The producer that produces the value when needed.
   */
  private Producer<? extends T> producer;

  /**
   * May or may not be storing a value.
   */
  private Maybe<T> value;

  /**
   * Factory method for a Lazy object.
   * This factory method takes in a value that is to be stored
   * in the value field.
   *
   * @param v The content that is to be stored in Maybe.
   * @return A Lazy object that stores the provided value.
   */
  public static <T> Lazy<T> of(T v) {
    return new Lazy<T>(v);
  }

  /**
   * Factory method for a Lazy Object.
   * This factory method takes in a Producer that has yet to produce
   * the value.
   *
   * @param s The Producer that will produce the value when needed.
   * @return A Lazy object that has a Producer which will produce the
   *         value when needed.
   */
  public static <T> Lazy<T> of(Producer<? extends T> s) {
    return new Lazy<T>(s);
  }

  /**
   * Private constructor for Lazy.
   *
   * @param s The Producer that produces the content when needed.
   */
  private Lazy(Producer<? extends T> s) {
    this.producer = s;
    this.value = Maybe.none();
  }

  /**
   * Private constructor for Lazy.
   *
   * @param val The content that is to be stored in Maybe.
   */
  private Lazy(T val) {
    this.value = Maybe.some(val);
  }

  /**
   * Retrieves the value of the Lazy object.
   * This method checks if the value has been computed and stored in the
   * Maybe object. If it has not been computed, the Producer produces the
   * value and stores it in the Maybe field, so that it does not need to
   * re-produce the value.
   *
   * @return The value that is stored or to be computed.
   */
  public T get() {
    T obj = this.value.orElse(this.producer);
    this.value = Maybe.of(obj);
    return obj;
  }

  /**
   * Returns the string representation of the Lazy<T> object.
   *
   * @return A string representing the Lazy<T> object.
   */
  @Override
  public String toString() {
    Transformer<T, String> transformer = x -> String.valueOf(x);
    return this.value.map(transformer).orElse(() -> "?");
  }

  /**
   * Transforms this.value (if any) using transformer and return a new Lazy U.
   *
   * @param transformer Transformer that transforms the Lazy object
   *                    storing Maybe T to a Lazy object storing Maybe U.
   * @return A Lazy object that now has a Producer that is ready to produce
   *         the content when needed.
   */
  public <U> Lazy<U> map(Transformer<? super T, ? extends U> transformer) {
    Producer<U> prod = () -> transformer.transform(this.get());
    return Lazy.of(prod);
  }

  /**
   * Transforms this.value (if any) using transformer and return a new Lazy U.
   *
   * @param transformer Transformer that transforms the Lazy object
   *                    storing Maybe T to a Lazy object storing Maybe U.
   * @return A Lazy object now has a Producer that is ready to produce
   *         the content when needed.
   */
  public <U> Lazy<U> flatMap(Transformer<? super T, ? extends Lazy<? extends U>> transformer) {
    Producer<U> prod = () -> (transformer.transform(this.get())).get();
    return Lazy.of(prod);
  }

  /**
   * Transform this.val (if any) depending on the result of condition.
   *
   * @param condition The BooleanCondition that is used to filter the Maybe
   *                  object.
   * @return A Lazy<Boolean> that result is only computed when the producer
   *         computes the result.
   */
  public Lazy<Boolean> filter(BooleanCondition<? super T> condition) {
    Producer<Boolean> prod = () -> condition.test(this.get());
    return Lazy.of(prod);
  }

  /**
   * Combines two Lazy objects into one Lazy object.
   *
   * @param lazy Lazy object to be combined with another Lazy object.
   * @param combiner A Combiner that will combine the Lazy objects together 
   *                 and return a single Lazy object.
   * @return A Lazy object that is the result of combining two Lazy objects
   *         together.
   */
  public <S, R> Lazy<R> combine(Lazy<S> lazy, 
      Combiner<? super T, ? super S, ? extends R> combiner) {
    Producer<R> prod = () -> combiner.combine(this.get(), lazy.get());
    return Lazy.of(prod);
  }

  /**
   * Checks if two Lazy objects have the same content value.
   *
   * @param object Lazy instance that will be checked against the current
   *               Lazy instance.
   * @return A boolean value representing whether the two Lazy instances have
   *         the same content value.
   */
  @Override
  public boolean equals(Object obj) {
    Lazy<?> other;
    
    if (!(obj instanceof Lazy<?>)) {
      return false;
    }

    other = (Lazy<?>) obj;

    return this.get().equals(other.get());
  }
}
