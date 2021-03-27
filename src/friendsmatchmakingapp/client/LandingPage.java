package friendsmatchmakingapp.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPage {

    private JTextField nameTextField;
    private JTextField socialMediaTextField;
    private JButton assignGameButton;


    public LandingPage() {
        assignGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1) get text in text field, create player object with assigned fields
                // 2) send request to server (server records the order of users)
                // 3) close the current panel and open the loading panel

                String name = nameTextField.getText();
                String socialMedia = socialMediaTextField.getText();
                Player player = new Player(name, socialMedia);
                // do later

                //


            }
        });
    }
}
