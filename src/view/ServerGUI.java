package view;

import model.server.Server;

import javax.swing.*;

import static main.Utility.isValidPort;

/**
 * Class linked to the ServerGUI form.
 * The class creates the JFrame, handles the input and starts the server thread
 */
public class ServerGUI {
    private JPanel panel1;
    private JTextField Lane1;
    private JTextField Lane2;
    private JTextField Lane3;
    private JPanel TextPanel;
    private JButton StartLane1;
    private JButton StartLane2;
    private JButton StartLane3;
    private JPanel CirclePanel;

    public static void init() {
        JFrame frame = new JFrame("ServerGUI");
        frame.setContentPane(new ServerGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        String port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        while (!isValidPort(port)) {
            JOptionPane.showMessageDialog(frame, "The port \"" + port + "\" is not a valid port.");
            port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        }
        new Thread(new Server(Integer.parseInt(port))).start();
    }
}