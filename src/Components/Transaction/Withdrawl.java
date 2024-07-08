package Components.Transaction;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Withdrawl extends JFrame implements ActionListener {

    JTextField t1;
    JButton b1, b2;
    JLabel l1, l2, l3;
    String accountNumber;

    Withdrawl(String accountNumber) {
        this.accountNumber = accountNumber;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Images/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 960, 1080);
        add(l3);

        l1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));

        b1 = new JButton("WITHDRAW");
        b2 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190, 350, 400, 20);
        l3.add(l1);

        l2.setBounds(190, 400, 400, 20);
        l3.add(l2);

        t1.setBounds(190, 450, 330, 30);
        l3.add(t1);

        b1.setBounds(390, 588, 150, 35);
        l3.add(b1);

        b2.setBounds(390, 633, 150, 35);
        l3.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            String amount = t1.getText();
            if (amount.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter the amount to withdraw");
            } else {
                double withdrawAmount = Double.parseDouble(amount);
                if (withdrawAmount > 10000) {
                    JOptionPane.showMessageDialog(null, "Maximum withdrawal limit is Rs.10,000");
                } else {
                    Date date = new Date();
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_management_system", "root", "root")) {
                        // Retrieve the current balance
                        double currentBalance = 0;
                        String balanceQuery = "SELECT balance FROM user_balance WHERE account_number = ?";
                        try (PreparedStatement balanceStmt = conn.prepareStatement(balanceQuery)) {
                            balanceStmt.setString(1, accountNumber);
                            ResultSet rs = balanceStmt.executeQuery();
                            if (rs.next()) {
                                currentBalance = rs.getDouble("balance");
                            }
                        }

                        if (currentBalance < withdrawAmount) {
                            JOptionPane.showMessageDialog(null, "Insufficient balance");
                        } else {
                            // Update the user_balance table
                            String insertSql = "INSERT INTO user_balance (account_number, time, type, balance) VALUES (?, ?, ?, ?)";
                            String updateSql = "UPDATE user_balance SET balance = ? WHERE account_number = ? AND type = ?";

                            try (
                                    PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                                    PreparedStatement updateStmt = conn.prepareStatement(updateSql)
                            ) {
                                // Set parameters for the insert statement
                                insertStmt.setString(1, accountNumber);
                                insertStmt.setTimestamp(2, timestamp);
                                insertStmt.setString(3, "Withdraw");
                                insertStmt.setDouble(4, withdrawAmount);
                                insertStmt.executeUpdate();

                                // Set parameters for the update statement
                                updateStmt.setDouble(1, currentBalance-withdrawAmount); // Assuming newBalance is the updated balance value
                                updateStmt.setString(2, accountNumber);
                                updateStmt.setString(3, "Deposit");
                                updateStmt.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
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
                            sendEmail(email, withdrawAmount);

                            JOptionPane.showMessageDialog(null, "Amount withdrawn successfully");
                            t1.setText("");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error withdrawing amount");
                    }
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
            message.setSubject("Withdraw Confirmation");
            message.setText("Dear customer, \n\nYou have successfully Withdraw an amount of $" + amount + " from your account.\n\nThank you for banking with us.");

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Withdrawl("").setVisible(true);
    }
}
