package view;

import javax.swing.*;

/**
 * This class together with the GUI test form are used for testing purposes since CirclePanel bugs the intellij Form
 * designer
 */
public class GUITest {
    private JPanel panel1;
    private JPanel TextPanel;
    private JTextField textField1;
    private JButton StartProducer;
    private JPanel ProducerPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUITest");
        frame.setContentPane(new GUITest().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createUIComponents() {
    }
}
