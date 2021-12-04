/**
 * @author Christopher Nugent
 * @since 2017-02-07
 */


public class LetterInventory {

    private static final int ALPHABET_LENGTH = 26;

    private int[] counters;
    int totalCount;

    /**
     * Initializes the fields in the LetterInventory to empty values.
     */
    LetterInventory() {
        /* Allocate space for the array */
        counters = new int[ALPHABET_LENGTH];
        /* Iterate over array with ForEach loop to assign 0's */
        for(int i:counters) {
            i = 0;
        }
        totalCount = 0;
    }

    /**
     * Initializes the fields in the LetterInventory from the data in the String.
     *
     *  @param input Data to be used for initialization
     */
    LetterInventory(String input) {
        /* Setup array */
        this();
        /* Iterate over the input String to allocate the appropriate counters */
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            /* Consider only letter characters */
            if (Character.isLetter(c)) {
                c = Character.toLowerCase(c);
                /* convenient way to get indices from character values */
                counters[getIndex(c)]++;
                totalCount++;
            }
        }
    }

    /**
     * Accesses the count of a specified character.
     *
     * @throws IllegalArgumentException If the character passed is non-alphabetic
     *
     * @param c Character to get the count of
     *
     * @return The count of the passed character in the LetterInventory
     */
    public int get(char c) {
        if(!Character.isLetter(c)) {
            throw new IllegalArgumentException("LetterInventory.get() can only accept alphabetic characters.");
        }
        return counters[getIndex(c)];
    }

    /**
     * Modifies the count of a specified character.
     *
     * @throws IllegalArgumentException If the character passed is non-alphabetic
     * @throws IllegalArgumentException If the integer passed is negative
     *
     * @param c Character to modify the count of
     * @param count The value to set the new count to
     */
    public void set(char c, int count) {
        if(!Character.isLetter(c)) {
            throw new IllegalArgumentException("LetterInventory.set() can only accept alphabetic characters.");
        } else if (count < 0) {
            throw new IllegalArgumentException("Cannot accept negative values.");
        }
        /* Modify the total count by delta of the counter of the letter */
        totalCount += count - counters[getIndex(c)];
        counters[getIndex(c)] = count;
    }

    /**
     * Returns the total of all counts in the LetterInventory
     *
     * @return The total of all counts in the LetterInventory
     */
    public int size() {
        return totalCount;
    }

    /**
     * Returns whether or not the LetterInventory is empty.
     *
     * @return True if the LetterInventory is empty, otherwise returns false
     */
    public boolean isEmpty() {
        return totalCount == 0;
    }

    public String toString() {
        String buffer = "[";
        for(int i = 0; i < ALPHABET_LENGTH; i++) {
            for(int j = 0; j < counters[i]; j++) {
                buffer += (char)('a' + i);
            }
        }
        return buffer + ']';
    }

    /**
     * Returns a new LetterInventory which contains the sum of the counts of this and another LetterInventory.
     *
     * @param other the LetterInventory to add to this
     * @return The sum of the two LetterInventory's as a LetterInventory
     */
    public LetterInventory add(LetterInventory other) {
        LetterInventory toReturn = new LetterInventory();
        for(int i = 0; i < ALPHABET_LENGTH; i++) {
            toReturn.counters[i] = counters[i] + other.counters[i];
        }
        toReturn.totalCount = totalCount + other.totalCount;
        return toReturn;
    }

    /**
     * Returns a new LetterInventory which contains the difference of the counts of this and another LetterInventory.
     *
     * @param other the LetterInventory to subtract from this
     * @return The difference of the two LetterInventory's as a LetterInventory
     */
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory toReturn = new LetterInventory();
        for(int i = 0; i < ALPHABET_LENGTH; i++) {
            if(counters[i] - other.counters[i] < 0) {
                return null;
            } else {
                toReturn.counters[i] = counters[i] - other.counters[i];
            }
        }
        toReturn.totalCount = totalCount - other.totalCount;
        return toReturn;
    }

    /**
     * Returns the percentage of a LetterInventory that is a certain letter as a decimal
     * @param c the character to get the percentage of
     * @return the perecentage of the LetterInventory that is that the passed character
     */
    public double getLetterPercentage(char c) {
        return get(c) / (double)totalCount;
    }

    /**
     * Gets the appropriate index in the array for the passed character.
     *
     * Assumes the character passed is alphabetic
     *
     * @param c The character to get the index for
     *
     * @return The index for the passed character
     */
    private static int getIndex(char c) {
        c = Character.toLowerCase(c);
        return (int)c - (int)'a';
    }
}
