/**
 * This class implements Maybe.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */ 

package cs2030s.fp;

public abstract class Maybe<T> {
  /**
   * The content that is to be stored.
   */
  private final T content;

  /**
   * Caching of the None instance to be returned.
   */
  private static final None<?> NONE = new None<>();

  /**
   * Constructor for Maybe.
   *
   * @param content The content to be stored in Maybe.
   */
  private Maybe(T content) {
    this.content = content;
  }

  /**
   * Factory method for Some object.
   *
   * @param content The content to be stored in Some container.
   * @return Some container storing the content.
   */
  public static <T> Maybe<T> some(T content) {
    return new Some<T>(content);
  }

  /**
   * Factory method for None object.
   *
   * @return An instance of the None object.
   */
  public static <T> Maybe<T> none() {
    // Maybe.NONE was instantiated at the start and is of type
    // None<?>. It is also declared as final and thus cannot be
    // re-assigned. The T value of field content for NONE is null which
    // can be returned by Maybe<T>::get() and is guaranteed to return
    // type T. Therefore, it is safe to cast `None<?>` to `None<T>`.
    @SuppressWarnings("unchecked")
    None<T> none = (None<T>) Maybe.NONE;
    
    // retrieves the cached None<T> instance and return it
    return none;
  }

  /**
   * Factory method for Maybe object.
   * Checks if the content pass in is null.
   * Returns a None instance if content is null.
   * Else, returns a Some instance.
   *
   * @param content The content to be stored.
   * @return An instance of Some<T> or None<T>
   */
  public static <T> Maybe<T> of(T content) {
    if (content == null) {
      return Maybe.<T>none();
    }
    return Maybe.<T>some(content);
  }

  /**
   * Retrieves the content in Maybe object.
   *
   * @return The content in the Maybe object.
   */
  protected T get() {
    return this.content;
  }

  public abstract <U> Maybe<U> map(Transformer<? super T, ? extends U> transformer);
  
  public abstract Maybe<T> filter(BooleanCondition<? super T> condition);

  public abstract <U> Maybe<U> 
      flatMap(Transformer<? super T, ? extends Maybe<? extends U>> transformer);

  public abstract T orElse(Producer<? extends T> producer);

  public abstract void ifPresent(Consumer<? super T> consumer);

  /**
   * Checks if two Maybe objects have the same content value.
   *
   * @param object  Maybe instance that will be checked against the current 
   *                Maybe instance.
   * @return A boolean value representing whether the two Maybe instances have
   *         the same content values.
   */
  @Override
  public boolean equals(Object object) {
    Maybe<?> container = null;

    // checks if the object is the same instance
    if (this == object) {
      return true;
    }

    // checks if the object is an instance of some unknown Maybe
    if (object instanceof Maybe<?>) {
      
      // checks if the object is an instance of None
      if (object instanceof None<?>) {
        // return false as the current instance can only be of type Some
        return false;
      }

      // cast the object to an unknown type of Maybe
      container = (Maybe<?>) object;
      
      // checks if the current Some instance's content is null
      if (this.content == null) {

        // checks if the other Some instance's content is also null
        return container.content == null;
      }

      // else, checks if the Some container contains the same content value as the 
      // current Some container
      return this.content.equals(container.content);
    }
    return false;
  }

  /**
   * Returns the string representation of the Maybe object.
   *
   * @return A string representing the Maybe (in this case, Some) object.
   */
  @Override
  public String toString() {
    String str = String.format("[%s]", content);
    return str;
  }

  /**
   * This nested static Some class encapsulates Some container.
   */
  private static final class Some<T> extends Maybe<T> {
    /**
     * Constructor for Some container.
     *
     * @param val The value that is to be stored in Some container.
     */
    private Some(T val) {
      super(val);
    }

    /**
     * Maps Some container to another Some container.
     *
     * @param transformer Transformer that transforms Some container storing T to
     *                    Some container storing U.
     * @return Some container that now stores a content of type U.
     */
    @Override 
    public <U> Some<U> map(Transformer<? super T, ? extends U> transformer) {
      return new Some<U>(transformer.transform(this.get()));
    }

    /**
     * Filters the Some object if it passes the BooleanCondition passed in.
     *
     * @param condition The BooleanCondition that is used to filter the Some object.
     *                  (i.e. is Even)
     */
    @Override
    public Maybe<T> filter(BooleanCondition<? super T> condition) {
      // if the value is null or it failed the test
      if (this.get() == null || (!condition.test(this.get()))) {
        // returns None<T>
        return Maybe.<T>none();
      }
      // else, returns the current instance
      return this;
    }
    
    /**
     * Flattens the mapping of Some container.
     *
     * @param transformer Transformer that transforms Some container storing T
     *                    to Some container storing U.
     * @return Some container that now stores a content of type U.
     */
    @Override
    public <U> Maybe<U> 
        flatMap(Transformer<? super T, ? extends Maybe<? extends U>> transformer) {
      // Since the transformer transforms Some container storing T to some
      // Maybe storing a subtype of U, therefore the output of the transofmer
      // will be some subtype of Maybe that stores a subtype of U. Since 
      // Maybe<U> is a subtype of Maybe<? extends U> and Maybe is a subtype of 
      // Maybe, therefore it is safe to cast `Maybe<? extends U>` to `Maybe<U>`.
      @SuppressWarnings("unchecked")
      Maybe<U> newMaybe = (Maybe<U>) transformer.transform(this.get());
      return newMaybe;
    }

    /**
     * Produces the current Some<T> instance's value.
     *
     * @param producer Producer that produces a value of type T.
     * @return A value of type T
     */
    @Override
    public T orElse(Producer<? extends T> producer) {
      return this.get();
    }

    /**
     * Consumes the current Some<T> instance's value.
     *
     * @param consumer Consumer that consumes a value of type T.
     */
    @Override
    public void ifPresent(Consumer<? super T> consumer) {
      consumer.consume(this.get());
    }
  }

  /**
   * This static nested None class encapsulates a None object.
   */
  private static final class None<T> extends Maybe<T> {
    /**
     * Contstructor for a None object.
     */
    private None() {
      super(null);
    }

    /**
     * Checks if two Maybe objects are equal.
     * Any instance of None<T> is equal to any other instance of None<T>.
     * Some<T> will never be equal to None<T>.
     * 
     * @param obj An instance of Maybe (either a Some or a None) that will be
     *            checked against the current None<T> instance.
     * @return A boolean value representing whether the two Maybe instances are
     *         equal.
     */
    @Override
    public boolean equals(Object obj) {
      // checks if the object is the same instance
      if (this == obj) {
        return true;
      }

      // checks if the object is an instance of None<T>
      return obj instanceof None<?>;
    }

    /**
     * Returns the string representation of the None<T> object.
     *
     * @return A string representing the None<T> object.
     */
    @Override
    public String toString() {
      return "[]";
    }

    /**
     * Maps None<T> object to the same None object.
     *
     * @param transformer Transformer that transform None object storing T to a
     *                    None object storing U.
     * @return The same None instance.
     */
    @Override
    public <U> Maybe<U> map(Transformer<? super T, ? extends U> transformer) {
      return Maybe.<U>none();
    }

    /**
     * Filters the None<T> object if it passes the BooleanCondition passed in.
     *
     * @param condition The BooleanCondition that is used to filter the None
     *                  object.
     */
    @Override
    public Maybe<T> filter(BooleanCondition<? super T> condition) {
      return Maybe.<T>none();
    }

    /**
     * Flattens the mapping of a None object.
     *
     * @param transformer Transformer that transforms None object storing T
     *                    to a None object storing U.
     * @return The same None instance.
     */
    @Override
    public <U> Maybe<U> 
        flatMap(Transformer<? super T, ? extends Maybe<? extends U>> transfromer) {
      return Maybe.<U>none();
    }

    /**
     * Produces the subtype of T that is produced by the Producer.
     *
     * @param producer Producer that produces a value of type T.
     * @return A value of type T.
     */
    @Override
    public T orElse(Producer<? extends T> producer) {
      return producer.produce();
    }

    /**
     * Consumer method that nothing for an instance of None<T>.
     *
     * @param consumer Consumer that does nothing.
     */
    @Override
    public void ifPresent(Consumer<? super T> consumer) {
      return;
    }
  }
}
