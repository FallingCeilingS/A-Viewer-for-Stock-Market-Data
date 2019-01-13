import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyTickerData implements Comparable<DailyTickerData> {
    private String date;
    private Date parsedDate;
    private double open, high, low, close;
    private long volume;
    private boolean isIncrease;

    public DailyTickerData(String[] item) throws ParseException {
        this.date = item[0].trim();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        parsedDate = dateFormat.parse(date);
        this.open = Double.parseDouble(item[1].trim());
        this.high = Double.parseDouble(item[2].trim());
        this.low = Double.parseDouble(item[3].trim());
        this.close = Double.parseDouble(item[4].trim());
        this.volume = Long.parseLong(item[5].trim());
        if (this.close - this.open > 0) {
            this.isIncrease = true;
        }
    }

    public String getDateString() {
        return this.date;
    }

    public Double getOpen() {
        return this.open;
    }

    public Double getLow() {
        return this.low;
    }

    public Double getHigh() {
        return this.high;
    }

    public Double getClose() {
        return this.close;
    }

    public Long getVolume() {
        return this.volume;
    }

    public boolean getIsIncrease() {
        return this.isIncrease;
    }

    @Override
    public int compareTo(DailyTickerData o) {
        if (parsedDate.getTime() - o.parsedDate.getTime() < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
