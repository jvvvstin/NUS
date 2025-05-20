public class Guessing {

    // Your local variables here
    private int low = 0;
    private int high = 1000;
    private int guess;

    /**
     * Implement how your algorithm should make a guess here
     */
    public int guess() {
        this.guess = this.low + (this.high - this.low) / 2;
        return this.guess;
    }

    /**
     * Implement how your algorithm should update its guess here
     */
    public void update(int answer) {
        if (answer == -1) {
            this.low = this.guess + 1;
        } else {
            this.high = this.guess;
        }
    }
}
