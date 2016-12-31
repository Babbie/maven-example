import javax.swing.*;

/**
 * Created by Sebastian on 31-12-2016.
 */
public class GUITest {
    private JPanel panel1;
    private JTextField Lane1;
    private JTextField Lane2;
    private JTextField Lane3;
    private JPanel TextPanel;
    private JPanel CirclePanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUITest");
        frame.setContentPane(new GUITest().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
