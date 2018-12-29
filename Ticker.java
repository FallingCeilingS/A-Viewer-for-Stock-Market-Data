public class Ticker implements Comparable<Ticker> {
    private String ticker;

    public Ticker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return ticker;
    }

    @Override
    public int compareTo(Ticker o) {
        return this.ticker.compareTo(o.getName());
    }
}
