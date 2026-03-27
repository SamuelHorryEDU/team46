import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Login implements ActionListener {

    private static JLabel usLabel;
    private static JLabel passswordLabel;
    private static JTextField usnText;
    private static JPasswordField passswordText;



    public static void main(String[] args) {

        // Fundementals for GUI to start
        JFrame frame = new JFrame();
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
        usnText.setBounds(100,20,165,25);
        panel.add(usnText);

        // PasswordLabel
        passswordLabel = new JLabel("password");
        passswordLabel.setBounds(10, 50, 80, 25);
        panel.add(passswordLabel);

        // Password Textbox
        passswordText = new JPasswordField(20);
        passswordText.setBounds(100,50, 165,25);
        panel.add(passswordText);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10,80,80,25);
        panel.add(loginButton);


        //Listeners
        loginButton.addActionListener(new Login() {});








        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usnText.getText();
        String password = passswordText.getText();
        System.out.println(username + ", " + password);

        if(username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(null, "Welcome Admin");
        } else if (username.equals("merchant") && password.equals("Merchant")) {
            JOptionPane.showMessageDialog(null, "Welcome Merchant");
            
        } else if (username.equals("manager") && password.equals("manager")) {
            JOptionPane.showMessageDialog(null, "Welcome Manager");
            
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect credentials.");
        }
    }
}