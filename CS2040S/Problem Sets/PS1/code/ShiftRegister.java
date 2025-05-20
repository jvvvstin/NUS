///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author Justin Ng (A0308249Y)
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
    /**
     * the size of the ShiftRegister
     */
    private int size;

    /**
     * the value/index of the tap
     */
    private int tap;

    /**
     * the seed/initial bits to be shifted
     */
    private int[] seed;

    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    /**
     * constructor for a ShiftRegister
     * @param size  the size of the ShiftRegister
     * @param tap   the value/index of the tap
     */
    ShiftRegister(int size, int tap) {
        // TODO:
        if (tap >= size || tap < 0) {
            throw new IllegalArgumentException();
        }
        this.size = size;
        this.tap = tap;
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed  the seed/initial bits to be shifted
     * Description: this function takes in a seed value and sets the
     *              ShiftRegister's seed to the value passed in
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        if (seed.length != this.size) {
            throw new IllegalArgumentException("Invalid seed size!");
        }
        for (int j : seed) {
            if (!(j == 0 || j == 1)) {
                throw new IllegalArgumentException("Invalid seed!");
            }
        }
        this.seed = seed;
    }

    /**
     * shift
     * @return the feedback bit
     * Description: this function does an entire linear left shift on the
     *              current seed, and returns the feedback bit
     */
    @Override
    public int shift() {
        // TODO:
        // retrieves the MSB
        int msb = this.seed[this.size - 1];

        // calculate the feedback bit
        int feedback = msb ^ this.seed[this.tap];
        int[] tmpSeed = new int[this.size];

        // loops through each bit of the (original) seed
        for (int i = 0; i < this.size - 1; i++) {
            // updates the new seed with the new shifted bits
            tmpSeed[i + 1] = this.seed[i];
        }

        // set the LSB of the new seed to the feedback
        tmpSeed[0] = feedback;
        this.seed = tmpSeed;
        return feedback;
    }

    /**
     * generate
     * @param k number of times to run the shift() function
     * @return the integer representation of a sequence of the generated feedback bits, of k length
     * Description:
     */
    @Override
    public int generate(int k) {
        // TODO:
        int result = 0;
        for (int i = 0; i < k; i++) {
            // 1 << i
            // basically, shifts the value of 1 by i times
            // i.e. 1 = 2^0 = 0001 in binary
            // if i = 2, "1" in 0001 is shifted 2 times to the left
            // becoming 0100 = 2^2
            result += this.shift() * (1 << (k - i - 1));
        }
        return result;
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array binary int array to be converted to decimal value
     * @return integer representation of its binary equivalent
     */
    private int toDecimal(int[] array) {
        // TODO:
        return 0;
    }

    /**
     * returns the string representation of the seed of the ShiftRegister
     * @return a string representing the seed of the ShiftRegister
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.size; i++) {
            sb.append(this.seed[this.size - i - 1]);
        }
        return sb.toString();
    }
}
