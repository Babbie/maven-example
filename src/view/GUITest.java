package view;

import javax.swing.*;

/**
 * Created by Sebastian on 2-1-2017.
 */
public class GUITest {
    private JPanel panel1;
    private JPanel TextPanel;
    private JTextField Lane1;
    private JTextField Lane2;
    private JTextField Lane3;
    private JPanel CirclePanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUITest");
        frame.setContentPane(new GUITest().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
