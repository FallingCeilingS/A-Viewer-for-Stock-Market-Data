import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;
import java.util.TreeSet;

public class CsvData {
    public static Set<DailyTickerData> TickerData = new TreeSet<>();

    public static void readFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(UrlData.filename));
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] item = line.split(",");
                DailyTickerData dailyTickerData = new DailyTickerData(item);
                TickerData.add(dailyTickerData);
            }
            for (DailyTickerData d : TickerData) {
                System.out.print(d.date + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
