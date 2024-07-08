package Components.Transaction;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Deposit extends JFrame implements ActionListener{

    JTextField t1;
    JButton b1, b2;
    JLabel l1, l3;
    String accountNumber;

    Deposit(String accountNumber) {
        this.accountNumber = accountNumber;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Images/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 960, 1080);
        add(l3);

        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));

        b1 = new JButton("DEPOSIT");
        b2 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190, 350, 400, 35);
        l3.add(l1);

        t1.setBounds(190, 420, 320, 35);
        l3.add(t1);

        b1.setBounds(390, 588, 150, 35);
        l3.add(b1);

        b2.setBounds(390, 633, 150, 35);
        l3.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            String amount = t1.getText();
            if (amount.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter the amount to deposit");
            } else {
                double depositAmount = Double.parseDouble(amount);
                Date date = new Date();
                java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_management_system", "root", "root")) {
                    // Update the user_balance table
                    double currentBalance = 0;
                    String balanceQuery = "SELECT balance FROM user_balance WHERE account_number = ?";
                    try (PreparedStatement balanceStmt = conn.prepareStatement(balanceQuery)) {
                        balanceStmt.setString(1, accountNumber);
                        ResultSet rs = balanceStmt.executeQuery();
                        if (rs.next()) {
                            currentBalance = rs.getDouble("balance");
                        }
                    }

                    // Update the user_balance table
                    String updateSql = "UPDATE user_balance SET time = ?, type = 'Deposit', balance = ? WHERE account_number = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setTimestamp(1, timestamp);
                        updateStmt.setDouble(2, currentBalance + depositAmount);
                        updateStmt.setString(3, accountNumber);
                        updateStmt.executeUpdate();
                    }

                    // Fetch email from login_info table
                    String email = "";
                    String emailQuery = "SELECT email FROM login_info WHERE account_number = ?";
                    try (PreparedStatement emailStmt = conn.prepareStatement(emailQuery)) {
                        emailStmt.setString(1, accountNumber);
                        ResultSet rs = emailStmt.executeQuery();
                        if (rs.next()) {
                            email = rs.getString("email");
                        }
                    }

                    // Send email notification
                    sendEmail(email, depositAmount);

                    JOptionPane.showMessageDialog(null, "Amount deposited successfully");
                    t1.setText("");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error depositing amount");
                }
            }
        } else if (ae.getSource() == b2) {
            // Handle the back button action here
            new Transaction(accountNumber);
            dispose();
            // Optionally, you can navigate to another frame
        }
    }

    private void sendEmail(String to, double amount) {
        final String username = "xunaeidmax@gmail.com";
        final String password = "zdjdwemvtxbkpivp";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Deposit Confirmation");
            message.setText("Dear customer, \n\nYou have successfully deposited an amount of $" + amount + " into your account.\n\nThank you for banking with us.");

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Deposit("").setVisible(true);
    }
}
