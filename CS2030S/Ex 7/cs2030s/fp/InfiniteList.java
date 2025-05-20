package cs2030s.fp;

import java.util.ArrayList;
import java.util.List;

/**
 * The InfiniteList class that may contain an arbitrary number of
 * elements.  The class is a generic class.
 * 
 * @author A0308249Y (Group 14B)
 * @param <T> The type to be stored in the InfiniteList.
 */
public class InfiniteList<T> {
  /**
   * Head of InfiniteList.
   */
  private final Lazy<Maybe<T>> head;
  
  /**
   * Tail of InfiniteList.
   */
  private final Lazy<InfiniteList<T>> tail;
  
  /**
   * Caching of the Sentinel instance to be returned.
   */
  private static final InfiniteList<?> SENTINEL = (InfiniteList<?>) new Sentinel();

  /**
   * Private constructor to set all fields null.
   */
  private InfiniteList() {
    this.head = null;
    this.tail = null;
  }

  /**
   * Private constructor given head and tail.
   * 
   * @param head The Lazy instance containing the head of the InfiniteList.
   * @param tail The Lazy instance to produce the tail of the InfiniteList.
   */
  private InfiniteList(Lazy<Maybe<T>> head, Lazy<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  /**
   * Generates an InfiniteList.  Given a producer that produces
   * a value x, generate the list as [x, x, x, ...]
   * 
   * @param <T> The type to be stored in the InfiniteList.
   * @param prod The producer to produce the value in the InfiniteList.
   * @return The created InfiniteList.
   */
  public static <T> InfiniteList<T> generate(Producer<T> prod) {
    return new InfiniteList<>(
          Lazy.of(() -> Maybe.some(prod.produce())),
          Lazy.of(() -> InfiniteList.generate(prod))
        );
  }

  /**
   * Generate an InfiniteList.  Given x and a lambda f, 
   * generate the list as [x, f(x), f(f(x)), f(f(f(x))), ...]
   * 
   * @param <T> The type to be stored in the InfiniteList.
   * @param init The first element.
   * @param next The transformation function on the element.
   * @return The created InfiniteList.
   */
  public static <T> InfiniteList<T> iterate(T init,
      Transformer<? super T, ? extends T> next) {
    return new InfiniteList<>(
          Lazy.of(Maybe.some(init)),
          Lazy.of(() -> InfiniteList.iterate(next.transform(init), next))
        );
  }

  /**
   * Generate an InfiniteList.  This is an empty InfiniteList.
   * 
   * @param <T> The type to be stored in the InfiniteList.
   * @return The created InfiniteList.
   */
  public static <T> InfiniteList<T> sentinel() {
    // SENTINEL was instantiated at the start and is of type
    // InfiniteList<?>. It is also declared as final and thus
    // cannot be re-assigned. The Lazy<Maybe<T>> value of field
    // head and Lazy<InfiniteList<T>> value of field tail is set
    // to null. The InfiniteList<T>::head() and tail() methods are
    // guaranteed to throw a NoSuchElementException and does not
    // return the head and tail values. Therefore, it is safe to
    // cast `InfiniteList<?>` to `InfiniteList<T>`.
    @SuppressWarnings("unchecked")
    InfiniteList<T> res = (InfiniteList<T>) SENTINEL;
    return res;
  }

  /**
   * Lazily search for the first element of the InfiniteList.
   * Then return the value of the first element of the InfiniteList.
   * 
   * @return the head of the InfiniteList.
   */
  public T head() {
    return this.head.get().orElse(() -> this.tail.get().head());
  }

  /**
   * Lazily search for the first element of the InfiniteList.
   * Then return the tail of the first element of the InfiniteList.
   * 
   * @return the tail of the InfiniteList.
   */
  public InfiniteList<T> tail() {
    return this.head.get()
               .map(x -> this.tail.get())
               .orElse(() -> this.tail.get().tail());
  }

  /**
   * Transform each element in the InfiniteList using
   * the given Transformer and return the resulting InfiniteList.
   * 
   * @param <U> The type of the resulting InfiniteList.
   * @param fn  The Transformer to transform 
   *            the element of the InfiniteList.
   * @return    A lazily evaluated InfiniteList with each
   *            element transformed using fn.
   */
  public <U> InfiniteList<U> map(Transformer<? super T, ? extends U> fn) {
    return new InfiniteList<>(
          this.head.map(mHead -> mHead.map(fn)),
          this.tail.map(mTail -> mTail.map(fn))
        );
  }

  /**
   * Check each element of the InfiniteList and filter out
   * elements that evaluate to `false` using the given
   * BooleanCondition.
   * 
   * @param pred The predicate to check element.
   * @return     A lazily evaluated InfiniteList with element
   *             failing the check removed.
   */
  public InfiniteList<T> filter(BooleanCondition<? super T> pred) {
    return new InfiniteList<>(
          this.head.map(mHead -> mHead.filter(pred)),
          this.tail.map(mTail -> mTail.filter(pred))
        );
  }

  /**
   * Truncates the InfiniteList to a finite list with at most
   * n elements.
   *
   * @param n The number of elements that is contained in the
   *          finite list.
   * @return  A finite list that contains at most n elements.
   */
  public InfiniteList<T> limit(long n) {
    if (n <= 0) {
      return InfiniteList.sentinel();
    }

    return new InfiniteList<T>(
      this.head,
      Lazy.of(() -> this.head.get()
          .map(x -> this.tail.get().limit(n - 1))
          .orElse(() -> this.tail.get().limit(n)))    
    );
  }

  /**
   * Converts the elements of the InfiniteList into a List where
   * the elements are in the same order as they appear in the 
   * InfiniteList.
   *
   * @return a List of elements that is in the InfiniteList.
   */
  public List<T> toList() {
    ArrayList<T> list = new ArrayList<>();
    this.head.get().ifPresent(elem -> list.add(elem));
    list.addAll(this.tail.get().toList());
    return list;
  }

  /**
   * Truncates the InfiniteList as soon as it finds an element that
   * evaluates the condition provided to false.
   *
   * @param pred The predicate/condition that is to be tested on each
   *             element
   * @return     A new truncated InfiniteList instance.
   */
  public InfiniteList<T> takeWhile(BooleanCondition<? super T> pred) {
    Lazy<Maybe<T>> lazyHead = Lazy.of(() -> Maybe.some(this.head()).filter(pred));
    Lazy<InfiniteList<T>> lazyTail = Lazy.of(() -> lazyHead.get()
        .map(h -> this.tail().takeWhile(pred))
        .orElse(() -> InfiniteList.sentinel()));
    return new InfiniteList<T>(lazyHead, lazyTail);
  }

  /**
   * Reduces the elements of the InfiniteList starting from the last
   * element of the InfiniteList, using the provided identity value 
   * and accumulator function, and returns the reduced value.
   *
   * @param <U> The type of the identity and the reduced value.
   * @param id  The initial identity value.
   * @param acc The accumulator function that is applied on the elements
   *            of the InfiniteList.
   * @return    The reduced value as a result of the accumulator function.
   */
  public <U> U foldRight(U id, Combiner<? super T, U, U> acc) {
    return this.tail.get().foldRight(this.head.get()
        .map(tail -> acc.combine(tail, id))
        .orElse(() -> id), acc);
  }

  /**
   * Returns the string representation of the InfiniteList instance.
   *
   * @return A string representing the InfiniteList instance.
   */
  @Override
  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }

  /**
   * Returns a boolean value that indicates whether the InfiniteList is
   * a Sentinel.
   *
   * @return A boolean value that indicates whether the InfiniteList is
   *         a Sentinel.
   */
  public boolean isSentinel() {
    return false;
  }

  /**
   * A nested static class that represents the end of the list.
   * The class contains nothing and performs no operation.
   */
  private static class Sentinel extends InfiniteList<Object> {
    /**
     * Private constructor of Sentinel.
     */
    private Sentinel() {
      super();
    }
    
    /**
     * Retrieving the head of a Sentinel should just throw an error.
     *
     * @throws java.util.NoSuchElementException As a Sentinel does not have a head.
     */
    @Override
    public Object head() {
      throw new java.util.NoSuchElementException();
    }

    /**
     * Retrieving the tail of a Sentinel should just throw an error.
     *
     * @throws java.util.NoSuchElementException As a Sentinel does not have a tail.
     */
    @Override
    public InfiniteList<Object> tail() {
      throw new java.util.NoSuchElementException();
    }

    /**
     * Mapping of a Sentinel should just return a Sentinel.
     *
     * @return A Sentinel.
     */
    @Override
    public <R> InfiniteList<R> map(Transformer<Object, ? extends R> mapper) {
      return InfiniteList.<R>sentinel();
    }

    /**
     * Filtering of a Sentinel should just return a Sentinel.
     *
     * @return A Sentinel.
     */
    @Override
    public InfiniteList<Object> filter(BooleanCondition<Object> predicate) {
      return InfiniteList.<Object>sentinel();
    }

    /**
     * Limiting of a Sentinel should just return a Sentinel.
     *
     * @return A Sentinel.
     */
    @Override
    public InfiniteList<Object> limit(long n) {
      return InfiniteList.sentinel();  
    }

    /**
     * Converting a Sentinel to a List should just return an empty List.
     *
     * @return An empty ArrayList.
     */
    @Override
    public List<Object> toList() {
      return new ArrayList<>();
    }

    /**
     * Truncating a Sentinel using takeWhile should just return a Sentinel.
     *
     * @return A Sentinel.
     */
    @Override
    public InfiniteList<Object> takeWhile(BooleanCondition<Object> pred) {
      return InfiniteList.sentinel();
    }

    /**
     * Reducing a Sentinel using foldRight should just return the identity
     * value.
     *
     * @return The initial identity value.
     */
    @Override
    public <U> U foldRight(U id, Combiner<Object, U, U> acc) {
      return id;
    }

    /**
     * Returns the string representation of the Sentinel instance.
     *
     * @return A String representing the Sentinel instance.
     */
    @Override
    public String toString() {
      return "~";
    }

    /**
     * Returns a boolean value that indicates whether the Sentinel is a
     * Sentinel.
     *
     * @return A boolean value that indicates whether the Sentinel is a
     * Sentinel.
     */
    @Override
    public boolean isSentinel() {
      return true;
    }
  }
}
