package Components.login;

import Components.Splash;
import Components.Transaction.Transaction;
//import Components.TransactionPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Login extends JFrame {

    private JLabel userLabel;
    private JTextField userText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JButton forgotPinButton;
    private JLabel logoLabel;
    private JLabel backgroundLabel;
    private JButton backButton;

    private String otp;
    private String userEmail;

    public Login() {
        // Setting the JFrame properties
        setTitle("Login Page");
        setSize(900, 600);
        setLocation(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating a JLayeredPane for layering components
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 600));

        // Adding background image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("Images/LoginBackground.jpg")); // Adjust path as needed
        Image backgroundFit = backgroundIcon.getImage().getScaledInstance(900, 600, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(backgroundFit);
        backgroundLabel = new JLabel(icon);
        backgroundLabel.setBounds(0, 0, 900, 600);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Adding logo at the top
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("Images/logo.png")); // Adjust path as needed
        Image img = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon icon2 = new ImageIcon(img);
        logoLabel = new JLabel(icon2);
        logoLabel.setBounds(400, 50, 100, 100);  // Adjust the position and size as needed
        layeredPane.add(logoLabel, Integer.valueOf(1));

        // Adding username label and text field
        userLabel = new JLabel("Account Number");
        userLabel.setBounds(350, 180, 200, 30);  // Adjust the position and size as needed
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setForeground(Color.BLACK);  // Optional: to ensure text is readable
        layeredPane.add(userLabel, Integer.valueOf(1));

        userText = new JTextField();
        userText.setBounds(350, 210, 200, 30);  // Adjust the position and size as needed
        userText.setFont(new Font("DIALOG_INPUT", Font.PLAIN, 16));
        layeredPane.add(userText, Integer.valueOf(1));

        // Adding password label and text field
        passwordLabel = new JLabel("PIN");
        passwordLabel.setBounds(350, 250, 200, 30);  // Adjust the position and size as needed
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.BLACK);  // Optional: to ensure text is readable
        layeredPane.add(passwordLabel, Integer.valueOf(1));

        passwordText = new JPasswordField();
        passwordText.setBounds(350, 280, 200, 30);  // Adjust the position and size as needed
        passwordText.setFont(new Font("Arial", Font.PLAIN, 16));
        layeredPane.add(passwordText, Integer.valueOf(1));

        // Adding Sign In button
        loginButton = new JButton("Sign In");
        loginButton.setBounds(350, 330, 200, 40);  // Adjust the position and size as needed
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setBackground(new Color(42, 172, 247));  // Set button background color
        loginButton.setForeground(Color.WHITE);  // Set button text color
        layeredPane.add(loginButton, Integer.valueOf(1));

        backButton = new JButton("Back");
        backButton.setBounds(350, 380, 200, 40);  // Adjust the position and size as needed
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(Color.BLACK);  // Set button background color
        backButton.setForeground(Color.WHITE);  // Set button text color
        layeredPane.add(backButton, Integer.valueOf(2));

        forgotPinButton = new JButton("Forgot PIN");
        forgotPinButton.setBounds(350, 430, 200, 40);  // Adjust the position and size as needed
        forgotPinButton.setFont(new Font("Arial", Font.PLAIN, 16));
        forgotPinButton.setBackground(Color.RED);  // Set button background color
        forgotPinButton.setForeground(Color.WHITE);  // Set button text color
        layeredPane.add(forgotPinButton, Integer.valueOf(2));

        // Adding the layered pane to the frame
        setContentPane(layeredPane);
        setVisible(true);

        // Adding action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Splash();
                dispose();
            }
        });

        forgotPinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleForgotPin();
            }
        });
    }

    private void handleLogin() {
        String accountNumber = userText.getText();
        String userPin = new String(passwordText.getPassword());

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/bank_management_system";
        String user = "root";
        String password = "root";  // Replace with your actual database password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM login_info WHERE account_number = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, accountNumber);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String getPin=rs.getString("pin");
                    if(userPin.equals(getPin)){ JOptionPane.showMessageDialog(this, "Login successful!");
                        new Transaction(accountNumber);
                        dispose();
                    }
                    else {

                        JOptionPane.showMessageDialog(this, "Invalid account number or PIN.");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Invalid account number or PIN.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during login.");
        }
    }

    private void handleForgotPin() {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter your account number:");

        if (accountNumber != null && !accountNumber.trim().isEmpty()) {
            // Database connection details
            String url = "jdbc:mysql://localhost:3306/bank_management_system";
            String user = "root";
            String password = "root";  // Replace with your actual database password

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                String sql = "SELECT email FROM login_info WHERE account_number = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, accountNumber);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        userEmail = rs.getString("email");
                        sendOtpToEmail(userEmail);
                        String enteredOtp = JOptionPane.showInputDialog(this, "Enter the OTP sent to your email:");
                        if (enteredOtp != null && enteredOtp.equals(otp)) {
                            String newPin = JOptionPane.showInputDialog(this, "Enter your new PIN:");
                            if (newPin != null && !newPin.trim().isEmpty()) {
                                updatePin(accountNumber, newPin);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid OTP.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Account number not found.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during PIN reset.");
            }
        }
    }

    private void sendOtpToEmail(String email) {
        otp = generateOtp();
        final String username = "xunaeidmax@gmail.com";
        final String password = "zdjdwemvtxbkpivp";


        // Set up email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Authenticate with the email account
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);

            Transport.send(message);
            JOptionPane.showMessageDialog(this, "OTP sent to your email.");
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send OTP.");
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a random 6-digit OTP
        return String.valueOf(otp);
    }

    private void updatePin(String accountNumber, String newPin) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/bank_management_system";
        String user = "root";
        String password = "root";  // Replace with your actual database password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE login_info SET pin = ? WHERE account_number = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newPin);
                stmt.setString(2, accountNumber);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "PIN updated successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating PIN.");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}