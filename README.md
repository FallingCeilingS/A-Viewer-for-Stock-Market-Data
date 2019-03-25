# A Viewer for Stock Market Data
Retrieve Data from Wall Street Journal.

When the program starts, a window should be displayed with drop down boxes for the
user to select a ticker symbol from a list, and select the start and end dates using drop down
boxes for the years, months and days. When the user has selected the dates and ticker
symbol, clicking on a button should retrieve the appropriate stock market data.
The data that is retrieved should then be displayed in a new window (i.e. a window
that is separate from the one used to select the ticker symbol and dates). This window
displays a graph showing the stock’s open, closing, high and low value for each day in the range that
was entered.

The main class is called `MarketGUI.java`, using Java version 1.8. The code should compile on the command line by executing
the command `javac MarketGUI.java` and run by executing the command `java
MarketGUI`.

This project only make use of the core Java Class Libraries, including JavaFX.

There is one window that
allows the user to select the input data (ticker symbol and dates) and another to display
the information retrieved from WSJ, as specified above in the requirements.
