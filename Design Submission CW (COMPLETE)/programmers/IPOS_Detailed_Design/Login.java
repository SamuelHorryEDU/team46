package IPOS_Detailed_Design;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login implements ActionListener {

    private static JLabel usLabel;
    private static JLabel passwordLabel;
    private static JTextField usnText;
    private static JPasswordField passwordText;
    private static JFrame frame;

    public static void main(String[] args) {

        // Fundamentals for GUI to start
        frame = new JFrame();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        // Username Label
        usLabel = new JLabel("username");
        usLabel.setBounds(10, 20, 80, 25);
        panel.add(usLabel);

        // Username Textbox
        usnText = new JTextField(20);
        usnText.setBounds(100, 20, 165, 25);
        panel.add(usnText);

        // Password Label
        passwordLabel = new JLabel("password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        // Password Textbox
        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        // Listeners
        loginButton.addActionListener(new Login());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usnText.getText();
        String password = new String(passwordText.getPassword());

        SAtoCA api = new SAtoCA();
        boolean success = api.authenticateMerchant(username, password);

        if (success) {
            JOptionPane.showMessageDialog(null, "Welcome Merchant");
            frame.dispose();
            new Dashboard();
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect credentials.");
        }
    }
}