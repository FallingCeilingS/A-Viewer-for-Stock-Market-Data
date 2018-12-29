import javax.swing.*;
import java.awt.*;

public class InputDataFrame extends JFrame {
    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private int width, height, locationX, locationY;

    public InputDataFrame() {
        setTitle("Input Data");
        width = (int) dimension.getWidth() / 2;
        height = (int) dimension.getHeight() / 2;
        setSize(width, height);
    }
}
