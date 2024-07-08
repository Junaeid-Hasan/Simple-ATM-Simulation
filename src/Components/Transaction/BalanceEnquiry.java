package Components.Transaction;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;

class BalanceEnquiry extends JFrame implements ActionListener {

    JTextField t1, t2;
    JButton b1, b2, b3;
    JLabel l1, l2, l3;
    String accountNumber;

    BalanceEnquiry(String accountNumber) {
        this.accountNumber = accountNumber;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Images/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 960, 1080);
        add(l3);

        l1 = new JLabel();
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190, 350, 400, 35);
        l3.add(l1);

        b1.setBounds(390, 633, 150, 35);
        l3.add(b1);
        double currentBalance = 0;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_management_system", "root", "root")) {
            // Update the user_balance table

            String balanceQuery = "SELECT balance FROM user_balance WHERE account_number = ?";
            try (PreparedStatement balanceStmt = conn.prepareStatement(balanceQuery)) {
                balanceStmt.setString(1, accountNumber);
                ResultSet rs = balanceStmt.executeQuery();
                if (rs.next()) {
                    currentBalance = rs.getDouble("balance");
                }
            }
        }catch (SQLException e){

        }

        l1.setText("Your Current Account Balance is  "+currentBalance);

        b1.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transaction(accountNumber).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("").setVisible(true);
    }
}
