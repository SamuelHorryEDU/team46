package IPOS_Detailed_Design;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class Dashboard extends JFrame {

    private JPanel Dashboard;
    private JButton button1;

    public Dashboard() {
        setContentPane(Dashboard);
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        button1.setText("Show Balance");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SAtoCA api = new SAtoCA();
                BigDecimal balance = api.getOutstandingBalance();
                JOptionPane.showMessageDialog(Dashboard.this, "Outstanding Balance: " + balance);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}