import java.util.Scanner;

class PE1 {
  public static void main(String[] args) {
    // Create a scanner to read from standard input.
    Scanner sc = new Scanner(System.in);
    Simulation simulation = new BusSimulation(sc);

    // Create a new simulator and run the simulation
    new Simulator(simulation).run();

    // Clean up the scanner.
    sc.close();
  }
}
