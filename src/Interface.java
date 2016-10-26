import javax.swing.*;
import view.SocketPanel;

/**
 * Created by Bab on 26-10-2016.
 */
public class Interface {
    private JPanel panel1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Interface");
        frame.setContentPane(new Interface().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
