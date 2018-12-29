import javax.swing.*;

public class MarketGUI {

    public static void main(String[] args) {
        System.out.println("execution");

        JFrame inputFrame = new InputDataFrame();
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setVisible(true);
    }
}
