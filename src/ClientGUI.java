import javax.swing.*;

/**
 * Created by Sebastian on 2-1-2017.
 */
public class ClientGUI {
    private JPanel panel1;
    private JTextField ClientLane;
    private JPanel CirclePanel;
    private JPanel TextPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientGUI");
        frame.setContentPane(new ClientGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
