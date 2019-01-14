/**
 * The class stores each ticker's name
 */

/**
 * Ticker class
 */
public class Ticker implements Comparable<Ticker> {
    // declare member variables
    private String ticker;

    /**
     * the constructor function
     * @param ticker String the string of ticker
     */
    public Ticker(String ticker) {
        this.ticker = ticker;
    }

    /**
     * get ticker's name
     * @return String the string of ticker
     */
    public String getName() {
        return ticker;
    }

    /**
     * compare and set the order of each item by the start of the word
     * @param o Ticker object
     * @return int the order symbol
     */
    @Override
    public int compareTo(Ticker o) {
        return this.ticker.compareTo(o.getName());
    }
}
