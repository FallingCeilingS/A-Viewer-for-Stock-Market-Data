import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class InputDataFrame extends JFrame {
    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private int width, height, locationX, locationY;

    public InputDataFrame() {
        setTitle("Input Data");
        width = (int) dimension.getWidth() / 2;
        height = (int) dimension.getHeight() / 2;
        setSize(width, height);

        Container container = getContentPane();

        JPanel topPanel = new JPanel();
        JLabel stockNameLabel = new JLabel("Select a Ticker: ");
        topPanel.add(stockNameLabel);
        TickerOptions.addOptions();
        String[] sortedTickers = TickerOptions.iterOptions();
        JComboBox<String> tickerOptions = new JComboBox<>(sortedTickers);
        tickerOptions.setRenderer(new ComboboxRenderer("- Please Select a Ticker -"));
        tickerOptions.setSelectedIndex(-1);
        topPanel.add(tickerOptions);
        tickerOptions.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println(tickerOptions.getSelectedItem());
                }
            }
        });
        container.add(topPanel);
    }
}

class ComboboxRenderer extends JLabel implements ListCellRenderer{
    private String title;

    public ComboboxRenderer(String title) {
        this.title = title;
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (index == -1 && value == null) {
            setText(title);
        } else {
            setText(value.toString());
        }
        return this;
    }
}
