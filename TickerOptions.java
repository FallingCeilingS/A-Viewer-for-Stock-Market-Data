/**
 * The class stores the collection of ticker options,
 * and is able to parse tickers' strings to build a correct URL
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */

import java.util.Set;
import java.util.TreeSet;

/**
 * the constructor function
 */
public class TickerOptions {
    /*
    declare and define class variables
     */
    private static String[] tickers = {
            "FOX (U.S.: Nasdaq) - 21st Century Fox Inc. Cl B",
            "AAPL (U.S.: Nasdaq) - Apple Inc.",
            "MSFT (U.S.: Nasdaq) - Microsoft Corp.",
            "AMZN (U.S.: Nasdaq) - Amazon.com Inc.",
            "GOOGL (U.S.: Nasdaq) - Alphabet Inc. Cl A",
            "TWTR (U.S.: NYSE) - Twitter Inc.",
            "SNE (U.S.: NYSE) - Sony Corp. ADR",
            "DIS (U.S.: NYSE) - Walt Disney Co.",
            "F (U.S.: NYSE) - Ford Motor Co.",
            "RYCEY (U.S.: OTC) - Rolls-Royce Holdings PLC ADR"
    };
    public static Set<Ticker> tickerOptions = new TreeSet<>();

    /*
    define class method
     */

    /**
     * add Ticker objects to the TreeSet
     */
    public static void addOptions() {
        for (String ticker : tickers) {
            tickerOptions.add(new Ticker(ticker));
        }
    }

    /**
     * get string of each Ticker objects in the tickerOptions object
     *
     * @return String[], a list of strings
     */
    public static String[] iterOptions() {
        String[] sortedTickers = new String[tickers.length];
        int index = 0;
        for (Ticker ticker : tickerOptions) {
            sortedTickers[index] = ticker.getName();
            index = index + 1;
        }
        return sortedTickers;
    }
}
