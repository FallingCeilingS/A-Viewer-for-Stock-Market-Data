/**
 * This class manipulate data from downloaded *.csv file,
 * storing each item to the TickerData Tree Set,
 * and set lower and upper bounds of the coordinate.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;
import java.util.TreeSet;

/**
 * CsvData class
 */
public class CsvData {
    /*
    declare class instance, TickerData, stores every item of the *.csv file with Set data structure
     */
    public static Set<DailyTickerData> TickerData;

    /*
    define class methods
     */
    /**
     * read *.csv file
     * also handle cases where it is missing some values
     */
    public static void readFile() {
        try {
            // initialise a new TreeSet
            TickerData = new TreeSet<>();
            // instantiate a new BufferedReader, storing the *.csv file respectively
            BufferedReader bufferedReader = new BufferedReader(new FileReader(UrlData.filename));
            // read the first line, which does not contain data
            bufferedReader.readLine();
            // initialise a string
            String line;
            // iterate to store items to the TreeSet (a collection of objects)
            while ((line = bufferedReader.readLine()) != null) {
                // split commas
                String[] item = line.split(",");
                // initialise a boolean to judge missing data
                boolean missingData = false;
                // in the condition that every item has the length of 6
                if (item.length == 6) {
                    // iteration to find empty (missing) value
                    for (String i : item) {
                        if (i.equals("")) {
                            missingData = true;
                            break;
                        }
                    }
                    // in the condition that every value in the item is not empty
                    if (!missingData) {
                        // instantiate a new DailyTickerData instance to store each item of data
                        DailyTickerData dailyTickerData = new DailyTickerData(item);
                        // add item to the TreeSet (a collection of objects) to manage these data
                        TickerData.add(dailyTickerData);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set lower bound of the diagram
     * @return Double the value of lower bound
     */
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

    /**
     * set upper bound of the diagram
     * @return Double the value of upper bound
     */
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

    /**
     * delete file when the user re-submit data
     */
    public static void deleteFile() {
        File file = new File(System.getProperty("user.dir")+ "/" + "HistoricalPrices.csv");
        if (file.exists()) {
            file.delete();
        }
    }
}
