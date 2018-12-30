public class UrlData {
    private static String url1 = "http://quotes.wsj.com/";
    private static String url2 = "/historical-prices/download?MOD_VIEW=page&num_rows=300&startDate=";
    private static String url3 = "&endDate=";
    public static String ticker;
    public static String startDate;
    public static String endDate;
    public static boolean validation = false;
    public static String url;

    public static void setUrl() {
       if (ticker != null && startDate != null && endDate!=null) {
           validation = true;
           url = url1 + ticker + url2 + startDate + url3 + endDate;
           System.out.println(url);
       }
    }
}
