package Components.Transaction;

import JDBC.ConnectDB;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener {

    JButton b1;
    JLabel l1;
    String pin;


    MiniStatement(String pin) {
        super("Mini Statement");

        this.pin = pin;
        getContentPane().setBackground(Color.WHITE);
        setSize(400, 600);
        setLocation(20, 20);

        l1 = new JLabel();
        add(l1);

        JLabel l2 = new JLabel("ABC Bank");
        l2.setBounds(150, 20, 100, 20);
        add(l2);

        JLabel l3 = new JLabel();
        l3.setBounds(20, 80, 300, 20);
        add(l3);

        JLabel l4 = new JLabel();
        l4.setBounds(20, 400, 300, 20);
        add(l4);

        setLayout(null);
        b1 = new JButton("Exit");
        add(b1);

        b1.addActionListener(this);

        l1.setBounds(20, 140, 400, 200);
        b1.setBounds(20, 500, 100, 25);

        fetchTransactions();
    }

    public void fetchTransactions() {
        try {
            ConnectDB conn = new ConnectDB(); // Assume Conn is your class for database connection
            ResultSet rs = conn.s.executeQuery("SELECT * FROM user_balance WHERE account_number = '" + pin + "'");

            StringBuilder transactions = new StringBuilder("<html>");

            while (rs.next()) {
                String date = rs.getString("time");
                String type = rs.getString("type");
                String amount=rs.getString("balance");
                transactions.append(date).append(" - ").append(type).append(" - ").append(amount).append("<br>");
            }
            transactions.append("</html>");

            l1.setText(transactions.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        this.setVisible(false);
    }

    public static void main(String[] args) {
        new MiniStatement("").setVisible(true);
    }
}
