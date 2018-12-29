import java.util.Set;
import java.util.TreeSet;

public class TickerOptions {
    private static String[] tickers = {
            "FOX - 21st Century Fox Inc. Cl B",
            "AAPL - Apple Inc."
    };
    public static Set<Ticker> tickerOptions = new TreeSet<>();

    public static void addOptions() {
        for (String ticker : tickers) {
            tickerOptions.add(new Ticker(ticker));
        }
    }

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
