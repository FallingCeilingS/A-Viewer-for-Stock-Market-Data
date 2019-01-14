/**
 * This class construct the Ticker data in a Collection data structure,
 * give some functions for access to draw figure and manipulate data outside class,
 * and override the compareTo function to order the item.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DailyTickerData class
 */
public class DailyTickerData implements Comparable<DailyTickerData> {
    /*
    declare instance variables
     */
    private String date;
    private Date parsedDate;
    private double open, high, low, close;
    private long volume;
    private boolean isIncrease;

    /**
     * the constructor function
     * @param item String[], an item parsed from *.csv file
     * @throws ParseException the exception that parse error
     */
    public DailyTickerData(String[] item) throws ParseException {
        // store data to instance variables
        this.date = item[0].trim();
        // parse data format
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        parsedDate = dateFormat.parse(date);
        this.open = Double.parseDouble(item[1].trim());
        this.high = Double.parseDouble(item[2].trim());
        this.low = Double.parseDouble(item[3].trim());
        this.close = Double.parseDouble(item[4].trim());
        this.volume = Long.parseLong(item[5].trim());
        // judge if the day the ticker price increased
        if (this.close - this.open > 0) {
            this.isIncrease = true;
        }
    }

    /*
    define instance methods
     */
    /**
     * get date in String format
     * @return String, date in "MM/dd/yyyy" format
     */
    public String getDateString() {
        return this.date;
    }

    /**
     * get the stock price at the start of the day
     * @return Double, the opening price data
     */
    public Double getOpen() {
        return this.open;
    }

    /**
     * get the lowest price the stock reached during the day
     * @return Double, the lowest price of the day
     */
    public Double getLow() {
        return this.low;
    }

    /**
     * the highest price the stock reached during the day
     * @return Double, the highest price of the day
     */
    public Double getHigh() {
        return this.high;
    }

    /**
     * get the stock price at the end of the day
     * @return Double, the closing price of the day
     */
    public Double getClose() {
        return this.close;
    }

    /**
     * get the number of shares traded that day
     * @return Long, the volume of shares traded of the day
     */
    public Long getVolume() {
        return this.volume;
    }

    /**
     * get the boolean that whether the stock increased in the day
     * @return boolean, show that whether the stock increased in the day
     */
    public boolean getIsIncrease() {
        return this.isIncrease;
    }

    /**
     * compare and set the order of each item by the date
     * @param o DailyTickerData, the object that contains single data
     * @return int, the order symbol
     */
    @Override
    public int compareTo(DailyTickerData o) {
        if (parsedDate.getTime() - o.parsedDate.getTime() < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
