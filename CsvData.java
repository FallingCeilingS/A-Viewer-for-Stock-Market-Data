import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;
import java.util.TreeSet;

public class CsvData {
    public static Set<DailyTickerData> TickerData;
    public static boolean show;

    public static void readFile() {
        try {
            TickerData = new TreeSet<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(UrlData.filename));
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] item = line.split(",");
                DailyTickerData dailyTickerData = new DailyTickerData(item);
                TickerData.add(dailyTickerData);
            }
            System.out.println(TickerData.isEmpty());
            System.out.println(TickerData.size());
            for (DailyTickerData d : TickerData) {
                System.out.print(d.getDateString() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Double setLowerBound() {
        double result = TickerData.iterator().next().getLow();
        for (DailyTickerData dailyTickerData : TickerData) {
            if (dailyTickerData.getLow() < result) {
                result = dailyTickerData.getLow();
            }
        }
        result = Math.round(result - 1) + 0.5;

        return result;
    }

    public static Double setUpperBound() {
        double result = TickerData.iterator().next().getHigh();
        for (DailyTickerData dailyTickerData : TickerData) {
            if (dailyTickerData.getHigh() > result) {
                result = dailyTickerData.getHigh();
            }
        }
        result = Math.round(result + 1) - 0.5;

        return result;
    }
}
