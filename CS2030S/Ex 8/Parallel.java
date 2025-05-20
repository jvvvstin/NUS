import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * Encapsulates a parallel computation.
 * Uses CompletableFuture.
 *
 * @author: Adi Yoga S. Prabawa
 * @version: CS2030S AY24/25 Semester 1, Ex 8
 */
public class Parallel extends Computation {
  /** Data source. */
  private Source source;

  /**
   * Constructor for this parallel computation.
   * Initializing data source.
   * 
   * @throws FileNotFoundException If the file not found.
   */
  public Parallel() throws FileNotFoundException {
    this.source = new Source();
  }

  /**
   * Running the computation in parallel.
   * The order should be kept.
   * 
   * @param town The town to be computed.
   * @return The string containing minimum price
   *         for each type for each block within town.
   */
  @Override
  public String run(String town) {
    CompletableFuture<String[]> blocksTask = CompletableFuture.supplyAsync(
        () -> this.source.findBlock(town));

    StringBuffer buffer = new StringBuffer();
    
    String[] blocks = blocksTask.join();
    
    CompletableFuture<?>[] typesTasks = new CompletableFuture<?>[blocks.length];

    for (int i = 0; i < blocks.length; i++) {
      String block = blocks[i];
      CompletableFuture<String> typeTask = CompletableFuture.supplyAsync(
          () -> this.processBlock(town, block));
      typesTasks[i] = typeTask;
    }

    CompletableFuture.allOf(typesTasks).join();
    for (int i = 0; i < typesTasks.length; i++) {
      buffer.append(typesTasks[i].join());
    }
    
    return buffer.toString();
  }

  /**
   * Process the price for each block in parallel.
   * Find the type, and for each type, find the minimum price.
   * The order should be kept.
   * 
   * @param town The town to be computed.
   * @param block The block to be computed.
   * @return The string containing minimum price
   *         for each type within block within town.
   */
  private String processBlock(String town, String block) {
    CompletableFuture<String[]> typesTask = CompletableFuture.supplyAsync(
        () -> this.source.findTypeInBlock(town, block));
    
    StringBuffer buffer = new StringBuffer();

    buffer.append(town + ", " + block + "\n");
    
    String[] types = typesTask.join();

    CompletableFuture<?>[] priceTasks = new CompletableFuture<?>[types.length];

    for (int i = 0; i < types.length; i++) {
      String type = types[i];
      CompletableFuture<Integer> priceTask = CompletableFuture.supplyAsync(
          () -> this.source.findMinPrice(town, block, type));
      priceTasks[i] = priceTask;
    }

    CompletableFuture.allOf(priceTasks).join();
    for (int i = 0; i < priceTasks.length; i++) {
      buffer.append("  " + types[i] + ": " + priceTasks[i].join().toString() + "\n");
    }

    return buffer.toString();
  }
  
  
  /**
   * The program read a single string indicating the town from
   * standard input.  Then it initializes the computation and
   * run the computation with the given town.  If the source data
   * is not found, the program catch the error and displays an
   * error message indicating file not found.
   * 
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    try {
      Parallel rent = new Parallel();
      System.out.println(rent.run(sc.next()));
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found!");
    } finally {
      sc.close();
    }
  }
}
