package friendsmatchmakingapp.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LandingPage extends JPanel {

    private JTextField nameTextField;
    private JTextField socialMediaTextField;
    private JButton assignGameButton;
    private ObjectOutputStream output;
    private MainPanel mainPanel;


    public LandingPage(ObjectOutputStream output, MainPanel mainPanel) {
        this.output = output;
        this.mainPanel = mainPanel;
        assignGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1) get text in text field, create player object with assigned fields
                // 2) send request to server (server records the order of users)
                // 3) close the current panel and open the loading panel

                String name = nameTextField.getText();
                String socialMedia = socialMediaTextField.getText();
                PlayerData player = new PlayerData(name, socialMedia, 0);
                try {
                    mainPanel.setName(name);
                    output.writeObject(new PlayerUpdate(null, "", name, socialMedia, ""));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });
    }
}
