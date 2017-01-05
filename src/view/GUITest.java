package view;

import javax.swing.*;

/**
 * This class together with the GUI test form are used for testing purposes since CirclePanel bugs the intellij Form
 * designer
 */
public class GUITest {
    private JPanel panel1;
    private JPanel TextPanel;
    private JTextField Lane1;
    private JTextField Lane2;
    private JTextField Lane3;
    private JPanel CirclePanel;
    private JButton StartStopLane3;
    private JButton StartStopLane2;
    private JButton StartStopLane1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUITest");
        frame.setContentPane(new GUITest().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
