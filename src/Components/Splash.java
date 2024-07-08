package Components;

import Components.Signup.Signup;
import Components.login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Splash extends JFrame {
    public Splash(){
        // Setting the JFrame properties
        setTitle("ABC Bank");
        setSize(900,600);
        setLocation(500,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating a JLayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 600));

        // Adding background image
        ImageIcon background = new ImageIcon(ClassLoader.getSystemResource("Images/background.jpg"));
        Image backgroundFit = background.getImage().getScaledInstance(900, 600, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(backgroundFit);
        JLabel imgLabel = new JLabel(icon);
        imgLabel.setBounds(0, 0, 900, 600);
        layeredPane.add(imgLabel, Integer.valueOf(0));

        // Adding welcome text
        JLabel welcomeText = new JLabel("Welcome To ABC Bank");
        welcomeText.setBounds(30, 150, 400, 50);  // Adjust the position and size as needed
        welcomeText.setForeground(Color.BLACK);  // Optional: to make the text more readable
        welcomeText.setFont(new Font("Serif", Font.BOLD, 24));  // Optional: to set font style and size
        layeredPane.add(welcomeText, Integer.valueOf(1));

        // Adding hero section text
        JLabel heroText = new JLabel("<html>Manage your finances with ease.<br>Secure, reliable, and user-friendly banking system.</html>");
        heroText.setBounds(30, 200, 400, 100);  // Adjust the position and size as needed
        heroText.setForeground(Color.BLACK);  // Optional: to make the text more readable
        heroText.setFont(new Font("Serif", Font.PLAIN, 18));  // Optional: to set font style and size
        layeredPane.add(heroText, Integer.valueOf(1));

        // Adding Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(35, 350, 150, 40);  // Adjust the position and size as needed
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        layeredPane.add(loginButton, Integer.valueOf(1));

        // Adding Signup button
        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(200, 350, 150, 40);  // Adjust the position and size as needed
        signupButton.setBackground(Color.BLACK);
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        layeredPane.add(signupButton, Integer.valueOf(1));

        // Adding action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();  // Close the splash screen
            }
        });
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Signup();
                dispose();  // Close the splash screen
            }
        });

        // Adding the layered pane to the JFrame
        setContentPane(layeredPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Splash();
    }
}
