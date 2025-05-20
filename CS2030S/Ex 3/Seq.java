/**
 * The Seq<T> for CS2030S 
 *
 * @author A0308249Y
 * @version CS2030S AY24/25 Semester 1
 */
public class Seq<T extends Comparable<T>> { // TODO: Change to bounded type parameter
  /**
   * An array of type T objects (array of Counters)
   */
  private T[] array;

  /**
   * Constructor for a Seq
   *
   * @param size  The size of the array (i.e. The number of Counters
   *              in the Coffee Shop.
   */
  public Seq(int size) {
    // TODO: add implementation
    // The only way we can put an object into array is through
    // the method set() and we can only put object of type T inside.
    // So it is safe to cast `Object[]` to `T[]`.
    @SuppressWarnings({"unchecked", "rawtypes"})
    T[] arr = (T[]) new Comparable[size];
    this.array = arr;
  }

  public void set(int index, T item) {
    // TODO: add implementation
    this.array[index] = item;
  }
  
  /**
   * Retrieves an instance of type T (i.e. a Counter).
   *
   * @param index The index of the item to be retrieved.
   * @return An instance of type T (i.e. a Counter).
   */
  public T get(int index) {
    return this.array[index]; // TODO: add implementation
  }

  /**
   * Retrieves the instance of type T that is of the smallest value.
   * i.e. retrieves the Counter with the shortest Queue
   *
   * @return An instance of type T of the smallest value (i.e. a Counter)
   */
  public T min() {
    T min = null;

    // loops through all the different instances of type T that is stored in
    // the array
    for (int i = 0; i < this.array.length; i++) {
      if (min == null) {
        min = this.array[i];
        continue;
      }
      int difference = min.compareTo(this.array[i]);
      if (difference > 0) {
        min = this.array[i];
      }
    }
    return min; // TODO: add implementation
  }

  /**
   * Retrieves the total number of items in the array.
   *
   * @return The total number of items in the array.
   */
  public int getNumOfCounters() {
    return this.array.length;
  }
  
  /**
   * Checks if there are any Counters with a non-full Queue.
   *
   * @return A boolean value representing if there are any Counters with a 
   *         non-full Queue.
   */
  public boolean areCountersQueuesFull() {
    boolean full = true;
    
    // loops through all the Counters stored in the array
    for (int i = 0; i < this.array.length; i++) {
      // checks if the elements in the array is an instance of Counter
      if (this.array[i] instanceof Counter) {
        Counter counter = (Counter) this.array[i];
        // checks if the Counter Queue is not full
        if (!counter.isCounterQueueFull()) {
          full = false;
          break;
        }
      }
    }
    return full;
  }

  /**
   * Returns the string representation of the Counters.
   *
   * @return A string represetning the Counters.
   */
  @Override
  public String toString() {
    String out = "[ ";
    for (int i = 0; i < array.length; i++) {
      out = out + i + ":" + array[i];
      if (i != array.length - 1) {
        out = out + ", ";
      }
    }
    return out + " ]";
  }
}
