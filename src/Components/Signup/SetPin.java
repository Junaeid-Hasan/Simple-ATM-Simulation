package Components.Signup;

import Components.Splash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetPin extends JFrame {

    private String accountNumber,email;
    private JTextField accountField;
    private JPasswordField pinField;
    private JPasswordField confirmPinField;
    private JButton confirmButton;

    public SetPin(String accountNumber,String email) {
        this.accountNumber = accountNumber;
        this.email=email;
        System.out.println(accountNumber);
        // Setting the JFrame properties
        setTitle("Set PIN");
        setSize(400, 300);
        setLocation(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating the JPanel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel accountLabel = new JLabel("Account NO :");
        accountLabel.setBounds(50, 50, 100, 30);
        panel.add(accountLabel);
        accountField = new JTextField();
        accountField.setBounds(150, 50, 200, 30);
        panel.add(accountField);

        // Adding components
        JLabel pinLabel = new JLabel("Enter PIN:");
        pinLabel.setBounds(50, 100, 100, 30);
        panel.add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(150, 100, 200, 30);
        panel.add(pinField);

        JLabel confirmPinLabel = new JLabel("Confirm PIN:");
        confirmPinLabel.setBounds(50, 150, 100, 30);
        panel.add(confirmPinLabel);

        confirmPinField = new JPasswordField();
        confirmPinField.setBounds(150, 150, 200, 30);
        panel.add(confirmPinField);

        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(150, 200, 100, 30);
        panel.add(confirmButton);

        // Adding the panel to the frame
        add(panel);
        setVisible(true);

        // Adding action listener to the confirm button
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleConfirm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void handleConfirm() throws SQLException {
        String pin = new String(pinField.getPassword());
        String confirmPin = new String(confirmPinField.getPassword());
        String ac=new String(accountField.getText());

        if (!pin.equals(confirmPin)) {
            JOptionPane.showMessageDialog(this, "PINs do not match. Please try again.");
            return;
        }
        if(!ac.equals(accountNumber)){
            JOptionPane.showMessageDialog(this, "Wrong Account Number");
            return;
        }

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/bank_management_system";
        String user = "root";
        String password = "root";  // Replace with your actual database password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql1 = "INSERT INTO login_info (account_number, pin, email) VALUES (?, ?, ?)";
            String sql2 = "INSERT INTO user_balance (account_number, time, type, balance) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt2 = conn.prepareStatement(sql2);
                 PreparedStatement stmt1 = conn.prepareStatement(sql1)) {

                // Insert into user_balance
                stmt2.setString(1, accountNumber);
                stmt2.setNull(2, java.sql.Types.TIMESTAMP);  // Assuming `time` is a TIMESTAMP
                stmt2.setNull(3, java.sql.Types.VARCHAR);    // Assuming `type` is a VARCHAR
                stmt2.setNull(4, 0);    // Assuming `balance` is a DECIMAL or NUMERIC
                stmt2.executeUpdate();

                // Insert into login_info
                stmt1.setString(1, accountNumber);
                stmt1.setString(2, pin);
                stmt1.setString(3, email);
                stmt1.executeUpdate();

                // Notify user on success
                JOptionPane.showMessageDialog(this, "PIN set successfully!");

                // Perform any UI changes on the event dispatch thread
                SwingUtilities.invokeLater(() -> {
                    new Splash();
                    dispose();
                });
        }
    }catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error setting PIN.");
        }

}

    public static void main(String[] args) {
        new SetPin("","");
    }}
