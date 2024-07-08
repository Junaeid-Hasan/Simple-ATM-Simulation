package Components.Transaction;

import JDBC.ConnectDB;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Pin extends JFrame implements ActionListener{

    JPasswordField t1, t2;
    JButton b1, b2;
    JLabel l1, l2, l3;
    String accountNumber;

    Pin(String accountNumber) {
        this.accountNumber = accountNumber;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Images/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l4 = new JLabel(i3);
        l4.setBounds(0, 0, 960, 1080);
        add(l4);

        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);

        l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);

        l3 = new JLabel("Re-Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(Color.WHITE);

        t1 = new JPasswordField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));

        t2 = new JPasswordField();
        t2.setFont(new Font("Raleway", Font.BOLD, 25));

        b1 = new JButton("CHANGE");
        b2 = new JButton("BACK");

        b1.addActionListener(this);
        b2.addActionListener(this);

        setLayout(null);

        l1.setBounds(280, 330, 800, 35);
        l4.add(l1);

        l2.setBounds(180, 390, 150, 35);
        l4.add(l2);

        l3.setBounds(180, 440, 200, 35);
        l4.add(l3);

        t1.setBounds(350, 390, 180, 25);
        l4.add(t1);

        t2.setBounds(350, 440, 180, 25);
        l4.add(t2);

        b1.setBounds(390, 588, 150, 35);
        l4.add(b1);

        b2.setBounds(390, 633, 150, 35);
        l4.add(b2);

        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            String newPin = String.valueOf(t1.getPassword());
            String reEnteredPin = String.valueOf(t2.getPassword());
            if (!newPin.equals(reEnteredPin)) {
                JOptionPane.showMessageDialog(null, "Entered PIN does not match");
                return;
            }
            if (newPin.equals("")) {
                JOptionPane.showMessageDialog(null, "PIN cannot be empty");
                return;
            }
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_management_system", "root", "root");
                String query = "UPDATE login_info SET pin = ? WHERE account_number = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(query)) {
                    updateStmt.setString(1, newPin);
                    updateStmt.setString(2, accountNumber);
                    int result = updateStmt.executeUpdate();

                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "PIN changed successfully");
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error changing PIN");
                    }
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error");
            }
        } else if (ae.getSource() == b2) {
            new Transaction(accountNumber);
            dispose();
        }
    }

    public static void main(String[] args) {
        new Pin("").setVisible(true);
    }
}
