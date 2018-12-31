public class UrlData {
    private static String url1 = "http://quotes.wsj.com/";
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

    public static void setUrl() {
       if (ticker != null && startDate != null && endDate!=null) {
           validation = true;
           url = url1 + ticker + url2 + startDate + url3 + endDate;
           System.out.println(url);
       }
    }

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
    }
}
