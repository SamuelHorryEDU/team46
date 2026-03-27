import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {

    private JPanel Dashboard;
    private JButton button1;

    public Dashboard() {
        setContentPane(Dashboard);
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setLocationRelativeTo(null);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Dashboard.this, "Welcome to the Dashboard");
            }
        });

    }

    public static void main(String[] args) {
        new Dashboard();
    }
}

