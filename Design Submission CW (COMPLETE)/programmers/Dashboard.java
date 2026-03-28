package IPOS_Detailed_Design;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class Dashboard extends JFrame {

    private JPanel dashboardPanel;
    private JButton button1;

    public Dashboard() {
        dashboardPanel = new JPanel();
        button1 = new JButton("Show Balance");

        dashboardPanel.add(button1);

        setContentPane(dashboardPanel);
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SAtoCA api = new SAtoCA();
                BigDecimal balance = api.getOutstandingBalance();
                JOptionPane.showMessageDialog(Dashboard.this, "Outstanding Balance: " + balance);
            }
        });
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}