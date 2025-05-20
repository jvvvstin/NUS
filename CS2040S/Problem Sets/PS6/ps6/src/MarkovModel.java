import java.util.HashMap;
import java.util.Random;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	public int order;

	public HashMap<String, int[]> table;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;

		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		int len = text.length();
		table = new HashMap<>();
		for (int i = 0; i < len - this.order; i++) {
			char nextChar = text.charAt(i + this.order);
			if (!this.table.containsKey(text.substring(i, i + this.order))) {
				int[] asciiValues = new int[256];
				asciiValues[nextChar] += 1;
				asciiValues[0]++;
				table.put(text.substring(i, i + this.order), asciiValues);
			} else {
				int[] asciiValues = this.table.get(text.substring(i, i + this.order));
				asciiValues[nextChar] += 1;
				asciiValues[0]++;
			}
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (kgram.length() != this.order) {
			return -1;
		}

		return this.table.get(kgram)[0];
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		if (kgram.length() != this.order) {
			return -1;
		}

		return this.table.get(kgram)[c];
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (kgram.length() != this.order || !this.table.containsKey(kgram)) {
			return NOCHARACTER;
		}
		int rand = generator.nextInt(this.table.get(kgram)[0]);
		int max = 0;
		int min = 0;
		for (int i = 1; i < this.table.get(kgram).length; i++) {
			max = min + this.table.get(kgram)[i] - 1;
			if (rand <= max && rand >= min) {
				return (char) i;
			}
			min = max + 1;
		}
		return NOCHARACTER;
	}

	public static void main(String[] args) {
		String text = "qwertyqwerty";
		int order = 2;
		int seed = 100;
		String kgram = "qw";
		char charAfter = 'e';

		MarkovModel testModel = new MarkovModel(order, seed);
		testModel.initializeText(text);
		char c = testModel.nextCharacter(kgram);
		System.out.println(c);
	}
}