/**
 * CS2030S AY22/23 Sem 2 PE1
 * @author: STUDENT ID
 * */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Encapsulate a list of TODO tasks.
 */
class TaskList {
  private int numOfTasks; // The number of tasks in the list.
  private Array<Integer> typeArray;  // Store the type of Task i.
  private Array<String> descriptionArray; // Store the description of Task i.
  private Array<Boolean> isCompletedArray; // Store whether Task i is completed or not.
  private Array<String> assigneeArray; // Store the name of the assignee of Task i (if any).
  private Array<Integer> dueInDaysArray; // Store the num of days before Task i is due (if any).
  private String errorMsg; // Store an error message.

  // The types of tasks
  private static final int ANYTIME = 0;
  private static final int DEADLINE = 1;
  private static final int ASSIGNABLE = 2;

  /**
   * A constructor that reads in the list from the standard input.
   **/
  public TaskList() {
    this(new Scanner(System.in));
  }

  /**
   * A constructor that reads in the list from the standard input.
   * @throws FileNotFoundException
   **/
  public TaskList(String filename) throws FileNotFoundException {
    this(new Scanner(new File(filename)));
  }

  /**
   * A constructor that reads in the list using the given scanner.
   * If the input contains an invalid type, print an error message.
   * @param sc The Scanner to read from.
   **/
  private TaskList(Scanner sc) {
    boolean success = this.loadTasks(sc);
    if (!success) {
      System.out.println(this.errorMsg);
    }
  }

  /**
   * Load the tasks from the given scanner.
   * @param sc The scanner to load from.
   * @return false if the input contains a wrong type; true otherwise.
   **/
  private boolean loadTasks(Scanner sc) {
    this.numOfTasks = Integer.parseInt(sc.nextLine().trim());
    this.typeArray = new Array<Integer>(this.numOfTasks);
    this.descriptionArray = new Array<String>(this.numOfTasks);
    this.isCompletedArray = new Array<Boolean>(this.numOfTasks);
    this.dueInDaysArray = new Array<Integer>(this.numOfTasks);
    this.assigneeArray = new Array<String>(this.numOfTasks);

    int i = 0;
    while (sc.hasNext()) {
      String text = sc.nextLine().trim();
      String[] arguments = text.split(",");
      boolean success = this.createTask(i, arguments);
      if (!success) {
        sc.close();
        return false;
      }
      i = i + 1;
    }
    return true;
  }

  /**
   * Create Task i with the given arguments.
   * @param i The index of the task.
   * @param args The arguments read from the input.
   * @return false if the input contains a wrong type; true otherwise.
   **/
  private boolean createTask(int i, String[] args) {
    int type = Integer.parseInt(args[0]);
    this.typeArray.set(i, type);
    String description = args[1];
    this.descriptionArray.set(i, description);
    this.isCompletedArray.set(i, false);

    if (type == TaskList.ANYTIME) {
      // no further action

    } else if (type == TaskList.DEADLINE) {
      int dueInDays = Integer.parseInt(args[2]);
      this.dueInDaysArray.set(i, dueInDays);

    } else if (type == TaskList.ASSIGNABLE) {
      int dueInDays = Integer.parseInt(args[2]);
      String assignees = args[3];
      this.dueInDaysArray.set(i, dueInDays);
      this.assigneeArray.set(i, assignees);

    } else {
      this.errorMsg = "Invalid task type in input: " + type;
      return false;
    }
    return true;
  }

  /**
   * Print the description of all tasks.
   **/
  public void printTaskDescriptions() {
    for (int i = 0; i < this.numOfTasks; i++) {
      String description = this.descriptionArray.get(i);
      System.out.println(i + " " + description);
    }
  }

  /**
   * Return a string representation of a task.
   * @param i The index of the task.
   * @return The string representation of Task i.
   **/
  private String taskDetailsOf(int i) {
    String s = "";

    boolean isCompleted = this.isCompletedArray.get(i);
    if (isCompleted) {
      s += "[X] ";
    } else {
      s += "[ ] ";
    }

    String description = this.descriptionArray.get(i);
    s += description;

    int type = this.typeArray.get(i);
    if (type == TaskList.DEADLINE) {
      int dueInDays = this.dueInDaysArray.get(i);
      s += " | Due in " + dueInDays + " days";

    } else if (type == TaskList.ASSIGNABLE) {
      int dueInDays = this.dueInDaysArray.get(i);
      String assignee = this.assigneeArray.get(i);
      s += " | Due in " + dueInDays + " days";
      s += " | Assigned to " + assignee;
    }
    return s;
  }

  /**
   * Print the details of all tasks.
   **/
  public void printTaskDetails() {
    for (int i = 0; i < this.numOfTasks; i++) {
      System.out.println(i + " " + this.taskDetailsOf(i));
    }
  }

  /**
   * Calculate the total reward points earned.
   * @return The reward points.
   **/
  public int getRewardPoints() {
    int sum = 0;
    for (int i = 0; i < this.numOfTasks; i++) {
      int type = this.typeArray.get(i);
      if (type == TaskList.DEADLINE || type == TaskList.ASSIGNABLE) {
        boolean isCompleted = this.isCompletedArray.get(i);
        if (isCompleted) {
          int dueInDays = this.dueInDaysArray.get(i);
          sum += dueInDays;
        }
      }
    }
    return sum;
  }

  /**
   * Print all the tasks that are due today.
   **/
  public void printDueToday() {
    for (int i = 0; i < this.numOfTasks; i++) {
      int type = this.typeArray.get(i);
      if (type == TaskList.DEADLINE || type == TaskList.ASSIGNABLE) {
        int dueInDays = this.dueInDaysArray.get(i); 
        if (dueInDays == 0) {
          System.out.println(i + " " + this.taskDetailsOf(i));
        }
      }
    }
  }

  /**
   * Remind users about all incomplete tasks with deadline.
   */
  public void remindAll() {
    for (int i = 0; i < this.numOfTasks; i++) {
      int type = this.typeArray.get(i);
      boolean isCompleted = this.isCompletedArray.get(i);
      if (!isCompleted) {

        if (type == TaskList.ASSIGNABLE) {
          String description = this.descriptionArray.get(i);
          String assignee = this.assigneeArray.get(i);
          System.out.println("Sending a reminder to complete \"" + description + "\" to " 
              + assignee);

        } else if (type == TaskList.DEADLINE) {
          String description = this.descriptionArray.get(i);
          int dueInDays = this.dueInDaysArray.get(i); 
          System.out.println("The task \"" + description + "\" is due in " + dueInDays 
              + " days"); 
        }
      }
    }
  }

  /**
   * Mark a task as complete.
   * @param i The index of the task to complete.
   */
  public void completeTask(int i) {
    this.isCompletedArray.set(i, true);
  }
}
