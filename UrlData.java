/**
 * The class manipulates the URL data.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/*
URLData class
 */
public class UrlData {
    /*
    declare class variables
     */
    private static String url1 = "https://quotes.wsj.com/";
    private static String url2 = "/historical-prices/download?MOD_VIEW=page&num_rows=300&startDate=";
    private static String url3 = "&endDate=";
    public static String ticker;
    public static String startDate;
    public static String endDate;
    public static boolean validation = false;
    public static String url;
    private static String errMsgTicker = "Invalid Input of Ticker. Please Select a Ticker!";
    private static String errMsgStartDate = "Invalid Input of Start Date. Please Select a Date!";
    private static String errMsgEndDate = "Invalid Input of End Date. Please Select a Date!";
    public static String errMsg = "";

    public static File file;
    public static String filename = "HistoricalPrices.csv";

    /*
    define class methods
     */

    /**
     * parse the string of ticker name, to get the ticker symbol name
     * @param val String, the value of ticker name
     * @return String, the ticker symbol name
     */
    private static String parseTicker(String val) {
        String ticker = "";
        for (int i = 0; i < val.length(); i++) {
            if (val.substring(i, i + 1).equals(" ")) {
                ticker = val.substring(0, i).trim();
                break;
            }
        }
        return ticker;
    }

    /**
     * if the data given by user is valid, concat strings to make an URL
     */
    public static void setUrl() {
       if (ticker != null && startDate != null && endDate != null) {
           validation = true;
           url = url1 + parseTicker(ticker) + url2 + startDate + url3 + endDate;
           System.out.println(url);
       }
    }

    /**
     * validate whether the necessary data is all given by users
     * if any part is invalid input, set relevant error message to remind the user to select again
     */
    public static void setValidation() {
        errMsg = "";
        if (ticker == null) {
            validation = false;
            errMsg = errMsg + "\n" + errMsgTicker;
        }
        if (startDate == null) {
            validation = false;
            errMsg = errMsg + "\n" + errMsgStartDate;
        }
        if (endDate == null) {
            validation = false;
            errMsg = errMsg + "\n" + errMsgEndDate;
        }
        errMsg = errMsg + "\n" + "Please close the window and try again!";
    }

    /**
     * if the URL is valid, retrieve data from the WSJ site
     * save *.csv file to the classpath
     */
    public static void saveUrlAs() {
        // find the classpath
        file = new File(System.getProperty("user.dir"));
        // if the URL is valid, set http connection
        if (validation) {
            if (!file.exists()) {
                file.mkdirs();
                System.out.println("not exist path");
            } else {
                System.out.println("Save file to: ");
                System.out.println(file.getPath());
                System.out.println(file.getAbsolutePath());
            }

            FileOutputStream fileOutputStream;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;

            try {
                URL httpUrl = new URL(url);

                // open connection
                httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
                // set time out period
                httpURLConnection.setConnectTimeout(60000);
                httpURLConnection.setReadTimeout(60000);
                // set the request method
                httpURLConnection.setRequestMethod("GET");
                // set other attributes
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(true);
                // connect to the URL
                httpURLConnection.connect();

                // if the connection is succeed (respond code is 200), download data
                if (httpURLConnection.getResponseCode() == 200) {

                    inputStream = httpURLConnection.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + "/" + filename);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                    byte[] bytes = new byte[1024];
                    int length = bufferedInputStream.read(bytes);

                    while (!Thread.interrupted() && length != -1) {
                        bufferedOutputStream.write(bytes, 0, length);
                        length = bufferedInputStream.read(bytes);
                    }

                    bufferedOutputStream.close();
                    bufferedInputStream.close();

                    // in this condition, the Internet is connected, so if the data is empty, it must be the reason that
                    // the user selected date range has no data
                    // set error message
                    errMsg = errMsg + "Internet Connection is OK.\n" +
                            "But in the Range of DATE for your Selected Ticker has not DATA to Display!\n" +
                            "Please Select another Range of DATE or another Ticker!\n";
                } else {
                    // in this case, the Internet connection is fine, but the response is not successful,
                    // that is, it is not the user's fault
                    // so the error message is to reveal the response code to the user
                    errMsg = errMsg + "Http Response Code: " + httpURLConnection.getResponseCode() + "\n";
                }

                // after connection, disconnect to the WSJ site
                httpURLConnection.disconnect();
            } catch (Exception e) {
                // in this case, the Internet connection is failed,
                // so the error message is to remind the user to set the Internet correctly
//                e.printStackTrace();
                errMsg = errMsg + "Internet Connection Failed!\n" +
                        "Please Check your Internet Settings!\n";
            }
        }
    }
}
