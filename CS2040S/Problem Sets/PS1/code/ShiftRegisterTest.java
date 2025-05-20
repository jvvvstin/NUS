import static org.junit.Assert.*;

import org.junit.Test;

/**
 * ShiftRegisterTest
 * @author Justin Ng (A0308249Y)
 * Description: set of tests for a shift register implementation
 */
public class ShiftRegisterTest {
    /**
     * Problem 2.b.
     * The program should validate that the size of the integer array, seed, is of the same value
     * as the size of the specified register. If the two values are different, then an exception
     * could be thrown to inform the user that there has been an error and that the size of the
     * seed does not match the size of the register.
     *
     * As for testing, test cases can be written for matching register and seed sizes (i.e. size
     * of register is 4 and length of seed is 4) as well as differing register and seed sizes (i.e.
     * size of register is 4 and length of seed is 6 etc.). The test would then have to check whether
     * the correct exception is being thrown for when the register and seed have differing sizes.
     */

    /**
     * Returns a shift register to test.
     * @param size the size of the ShiftRegister
     * @param tap the value/index of the tap
     * @return a new shift register
     */
    ILFShiftRegister getRegister(int size, int tap) {
        return new ShiftRegister(size, tap);
    }

    /**
     * Tests shift with simple example.
     */
    @Test
    public void testShift1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.shift());
        }
    }

    /**
     * Tests generate with simple example.
     */
    @Test
    public void testGenerate1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 6, 1, 7, 2, 2, 1, 6, 6, 2, 3 };
        for (int i = 0; i < 10; i++) {
            assertEquals("GenerateTest", expected[i], r.generate(3));
        }
    }

    /**
     * Tests register of length 1.
     */
    @Test
    public void testOneLength() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = { 1 };
        r.setSeed(seed);
        int[] expected = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.generate(3));
        }
    }

    /**
     * Tests with erroneous seed.
     */
    @Test
    public void testError() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = { 1, 0, 0, 0, 1, 1, 0 };
//        r.setSeed(seed);
        assertThrows(IllegalArgumentException.class, () -> r.setSeed(seed));
//        r.shift();
//        r.generate(4);
    }

    /**
     * Test creating ShiftRegister of size 0
     */
    @Test
    public void testRegisterOfSize0() {
        assertThrows(IllegalArgumentException.class, () -> getRegister(0, 0));
    }

    /**
     * Test creating ShiftRegister whereby the tap value is larger than the register
     */
    @Test
    public void testTapLargerThanRegister() {
        assertThrows(IllegalArgumentException.class, () -> getRegister(5, 7));
    }

    /**
     * Test creating ShiftRegister whereby the tap value is same as the size of the register
     * (index of tap would be out of bounds)
     */
    @Test
    public void testTapSameSizeAsRegister() {
        assertThrows(IllegalArgumentException.class, () -> getRegister(5, 5));
    }

    /**
     * Test creating ShiftRegister whereby the length of the seed is smaller than the register
     */
    @Test
    public void testSettingSeedSmallerThanRegister() {
        ILFShiftRegister r = getRegister(5, 3);
        assertThrows(IllegalArgumentException.class, () -> r.setSeed(new int[] { 0, 1 }));
    }

    /**
     * Test creating ShiftRegister whereby the length of the seed is larger than the register
     */
    @Test
    public void testSettingSeedLargerThanRegister() {
        ILFShiftRegister r = getRegister(5, 3);
        assertThrows(IllegalArgumentException.class, () -> r.setSeed(new int[] { 0, 1, 0, 1, 0, 1 }));
    }

    /**
     * Test creating ShiftRegister of negative value tap
     */
    @Test
    public void testSettingNegativeTap() {
        assertThrows(IllegalArgumentException.class, () -> getRegister(5, -7));
    }

    /**
     * Test creating ShiftRegister of negative value size
     */
    @Test
    public void testSettingNegativeSize() {
        assertThrows(IllegalArgumentException.class, () -> getRegister(-5, 3));
    }

    /**
     * Test with an invalid seed
     */
    @Test
    public void testSettingInvalidSeed() {
        ILFShiftRegister r = getRegister(5, 3);
        assertThrows(IllegalArgumentException.class, () -> r.setSeed(new int[] {0, 1, 2, 0, 1}));
    }
}
