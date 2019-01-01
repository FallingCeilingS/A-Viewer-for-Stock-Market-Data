import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyTickerData implements Comparable<DailyTickerData> {
    public String date;
    public Date parsedDate;
    private double open, high, low, close;
    private long volume;

    public DailyTickerData(String[] item) throws ParseException {
        this.date = item[0].trim();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        parsedDate = dateFormat.parse(date);
//        System.out.print("get time" + parsedDate.getTime());
        this.open = Double.parseDouble(item[1].trim());
        this.high = Double.parseDouble(item[2].trim());
        this.low = Double.parseDouble(item[3].trim());
        this.close = Double.parseDouble(item[4].trim());
        this.volume = Long.parseLong(item[5].trim());
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
