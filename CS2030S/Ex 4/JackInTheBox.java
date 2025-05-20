/**
 * This class implements a Jack in the Box Transformer.
 * Takes in an item and returns an item in Some container.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */ 

class JackInTheBox<T> implements Transformer<T, Some<T>> {
  @Override
  public Some<T> transform(T arg) {
    // return the content, in a Some container
    return Some.<T>some(arg);
  }
}
