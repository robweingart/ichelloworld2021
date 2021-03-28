package friendsmakingapp.client;

import friendsmakingapp.util.GameStateUpdate;
import friendsmakingapp.util.PlayerData;
import friendsmakingapp.util.PlayerUpdate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class MainPanel extends JFrame {
    private JPanel panel1;
    private JLabel timerLabel;
    private JTable table1;
    private JPanel whiteboardSpace;
    private JTextField inputField;
    private JTextArea chatArea;
    private JButton chatButton;
    private String name;
    private WhiteboardPanel whiteboardPanel;

    private List<PlayerData> participants;
    private String chat;
    private int current;
    private int currentRound;
    public static final int NUMBER_OF_ROUNDS = 5;

    private Timer timer;
    private int interval;
    int delay = 1000;
    int period = 1000;
    ObjectOutputStream output;

    public void setInfo(String name, String details) {
        this.name = name;
        System.out.println(name);

        try {
            if (output == null){

                Socket socket = new Socket("localhost", 5000);
                output = new ObjectOutputStream(socket.getOutputStream());
                ClientThread clientThread = new ClientThread(socket, this);
                clientThread.start();

            }

            output.writeObject(new PlayerUpdate(null, "", name, details, ""));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public MainPanel() throws IOException {
        this.participants = participants;
        current = 0;

        setContentPane(new LandingPage(this));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    public void incrementTime() {

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                System.out.println(setInterval());

            }
        }, delay, period);
    }


    private int setInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }


    public PlayerData getNextPlayer() {
        PlayerData res = participants.get(current % participants.size());
        current++;
        return (res);

    }

    public void sendMessage(){
        try {
            output.writeObject(new PlayerUpdate("", inputField.getText(), "", "", ""));
            inputField.setText("");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // implement logic if user leaves game later
    // timer (display the result at the end of each round for 5 seconds when it reaches 0, then start next round)
    // scoreboard (display each users' name and current score)
    // chat log (display chat history)
    // input field (record answers and chat, reports if answer is correct and update scoreboard)
    // drawboard (display drawing)
    // questions (display current question)
    // game ends if a user reaches 3000 points or more


    public static void main(String[] args) {
        try {
            MainPanel mainWindow = new MainPanel();
            mainWindow.setBounds(0, 0, 500, 300);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void update(GameStateUpdate readObject) {
        if (getContentPane() instanceof LandingPage) {
            if (readObject.currentDrawer.equals(name)) {
                setContentPane(new AnswerPrompt(output, readObject.currentQuestion));
            } else {
                setContentPane(panel1);
                createWhiteboard();
            }
        } else if (getContentPane() instanceof AnswerPrompt) {
            setContentPane(panel1);
            createWhiteboard();
            whiteboardPanel.setDrawing(true);

        } else if (getContentPane() == (panel1)) {

            chat = readObject.chat;

            chatArea.setText(chat);

            panel1.setBounds(30, 30, 500, 300);
            // update list of players
            if (readObject.currentDrawer.equals(name)) {
                inputField.setEnabled(false);
                whiteboardPanel.setDrawing(true);
            } else {
                inputField.setEnabled(true);
                whiteboardPanel.setDrawing(false);
                whiteboardPanel.drawLines(readObject.lines);
            }
        }
        revalidate();
        repaint();


    }

    public void createWhiteboard(){

        System.out.println("Heya");
        whiteboardPanel = new WhiteboardPanel(output);
        // attach whiteboardPanel as child of panel1
        whiteboardPanel.setBounds(0, 0, 1000, 1000);
        whiteboardSpace.add(whiteboardPanel);
        whiteboardSpace.setVisible(true);

        whiteboardPanel.revalidate();
        whiteboardPanel.repaint();

        whiteboardSpace.revalidate();
        whiteboardSpace.repaint();
        panel1.revalidate();
        panel1.repaint();
    }
}
