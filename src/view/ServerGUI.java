package view;

import main.Lane;
import main.LaneThread;
import main.ThreadListener;
import model.server.Server;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static main.Utility.isValidPort;

/**
 * Class linked to the ServerGUI form.
 * The class creates the JFrame, handles the input and starts the server thread
 */
public class ServerGUI implements ThreadListener {
    private static JFrame frame;
    private JPanel panel1;
    private JTextField Lane1;
    private JTextField Lane2;
    private JTextField Lane3;
    private JPanel TextPanel;
    private JButton StartLane1;
    private JButton StartLane2;
    private JButton StartLane3;
    private JPanel CirclePanel;

    private ServerGUI(){
        ServerGUI thisServer = this;
        StartLane1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisServer.start(Lane.First);
                StartLane1.setEnabled(false);
            }
        });
        StartLane2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisServer.start(Lane.Second);
                StartLane1.setEnabled(false);
            }
        });
        StartLane2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisServer.start(Lane.Third);
                StartLane1.setEnabled(false);
            }
        });
    }

    public static void init() {
        frame = new JFrame("ServerGUI");
        frame.setContentPane(new ServerGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void start(Lane lane) {
        String port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        while (!isValidPort(port)) {
            JOptionPane.showMessageDialog(frame, "The port \"" + port + "\" is not a valid port.");
            port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        }
        Server serverThread = new Server(Integer.parseInt(port), lane);
        serverThread.start();
        serverThread.addListener(this);
    }

    @Override
    public void threadUpdate(LaneThread laneThread) {
        switch (laneThread.getLane()) {
            case First:
                Lane1.setText(laneThread.getMessage());
                StartLane1.setEnabled(laneThread.isDone());
                break;
            case Second:
                Lane2.setText(laneThread.getMessage());
                StartLane2.setEnabled(laneThread.isDone());
                break;
            case Third:
                Lane2.setText(laneThread.getMessage());
                StartLane2.setEnabled(laneThread.isDone());
                break;
        }
    }
}