package Components.Signup;

import Components.Splash;
import Components.mailSender.SendEmail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Signup extends JFrame {

    private JTextField nameField;
    private JTextField fathersNameField;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;
    private JRadioButton male;
    private JRadioButton female;
    private JRadioButton otherGender;
    private JRadioButton married;
    private JRadioButton unmarried;
    private JRadioButton otherMaritalStatus;
    private JTextField nidField;
    private JTextArea addressArea;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField pinCodeField;
    private JButton signupButton;
    private JButton backButton;


    public Signup() {
        // Setting the JFrame properties
        setTitle("Signup Page");
        setSize(900, 600);
        setLocation(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating a JLayeredPane for layering components
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 600));

        // Adding background image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("Images/signup.jpg")); // Adjust path as needed
        Image backgroundFit = backgroundIcon.getImage().getScaledInstance(900, 600, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(backgroundFit);
        JLabel backgroundLabel = new JLabel(icon);
        backgroundLabel.setBounds(0, 0, 900, 600);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Adding heading
        JLabel heading = new JLabel("Signup Form");
        heading.setBounds(350, 20, 200, 40);
        heading.setFont(new Font("Serif", Font.BOLD, 20));
        heading.setForeground(Color.BLACK);
        layeredPane.add(heading, Integer.valueOf(1));

        // Adding labels and fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 200, 30);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 16));

        layeredPane.add(nameLabel, Integer.valueOf(1));

        nameField = new JTextField();
        nameField.setBounds(250, 80, 255, 30);
        layeredPane.add(nameField, Integer.valueOf(1));

        JLabel fathersNameLabel = new JLabel("Father's Name:");
        fathersNameLabel.setBounds(50, 120, 200, 30);
        fathersNameLabel.setFont(new Font("Serif", Font.BOLD, 16));

        layeredPane.add(fathersNameLabel, Integer.valueOf(1));

        fathersNameField = new JTextField();
        fathersNameField.setBounds(250, 120, 255, 30);
        layeredPane.add(fathersNameField, Integer.valueOf(1));

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(50, 160, 200, 30);
        dobLabel.setFont(new Font("Serif", Font.BOLD, 16));

        layeredPane.add(dobLabel, Integer.valueOf(1));

        // Create JComboBoxes for day, month, year
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        dayComboBox = new JComboBox<>(days);
        dayComboBox.setBounds(250, 160, 60, 30);
        layeredPane.add(dayComboBox, Integer.valueOf(1));

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setBounds(320, 160, 100, 30);
        layeredPane.add(monthComboBox, Integer.valueOf(1));

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[100];
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        yearComboBox = new JComboBox<>(years);
        yearComboBox.setBounds(430, 160, 80, 30);
        layeredPane.add(yearComboBox, Integer.valueOf(1));

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 200, 200, 30);
        genderLabel.setFont(new Font("Serif", Font.BOLD, 16));

        layeredPane.add(genderLabel, Integer.valueOf(1));

        male = new JRadioButton("Male");
        male.setBounds(250, 200, 70, 30);
        male.setBackground(new Color(0, 0, 0, 0)); // Make background transparent
        layeredPane.add(male, Integer.valueOf(1));

        female = new JRadioButton("Female");
        female.setBounds(330, 200, 80, 30);
        female.setBackground(new Color(0, 0, 0, 0)); // Make background transparent
        layeredPane.add(female, Integer.valueOf(1));

        otherGender = new JRadioButton("Other");
        otherGender.setBounds(420, 200, 80, 30);
        otherGender.setBackground(new Color(0, 0, 0, 0)); // Make background transparent
        layeredPane.add(otherGender, Integer.valueOf(1));

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(otherGender);

        JLabel maritalStatusLabel = new JLabel("Marital Status:");
        maritalStatusLabel.setBounds(50, 280, 200, 30);
        maritalStatusLabel.setFont(new Font("Serif", Font.BOLD, 16));

        layeredPane.add(maritalStatusLabel, Integer.valueOf(1));

        married = new JRadioButton("Married");
        married.setBounds(250, 280, 80, 30);
        married.setBackground(new Color(0, 0, 0, 0)); // Make background transparent
        layeredPane.add(married, Integer.valueOf(1));

        unmarried = new JRadioButton("Unmarried");
        unmarried.setBounds(340, 280, 100, 30);
        unmarried.setBackground(new Color(0, 0, 0, 0)); // Make background transparent
        layeredPane.add(unmarried, Integer.valueOf(1));

        otherMaritalStatus = new JRadioButton("Other");
        otherMaritalStatus.setBounds(450, 280, 80, 30);
        otherMaritalStatus.setBackground(new Color(0, 0, 0, 0)); // Make background transparent
        layeredPane.add(otherMaritalStatus, Integer.valueOf(1));

        ButtonGroup maritalStatusGroup = new ButtonGroup();
        maritalStatusGroup.add(married);
        maritalStatusGroup.add(unmarried);
        maritalStatusGroup.add(otherMaritalStatus);

        JLabel nidLabel = new JLabel("NID No:");
        nidLabel.setBounds(50, 320, 255, 30);
        nidLabel.setFont(new Font("Serif", Font.BOLD, 16));

        layeredPane.add(nidLabel, Integer.valueOf(1));

        nidField = new JTextField();
        nidField.setBounds(250, 320, 255, 30);
        layeredPane.add(nidField, Integer.valueOf(1));

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 360, 200, 30);
        addressLabel.setFont(new Font("Serif", Font.BOLD, 16));

        layeredPane.add(addressLabel, Integer.valueOf(1));

        addressArea = new JTextArea();
        addressArea.setBounds(250, 360, 255, 60);
        layeredPane.add(addressArea, Integer.valueOf(1));

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(50, 430, 200, 30);
        cityLabel.setFont(new Font("Serif", Font.BOLD, 16));

        layeredPane.add(cityLabel, Integer.valueOf(1));

        cityField = new JTextField();
        cityField.setBounds(250, 430, 255, 30);
        layeredPane.add(cityField, Integer.valueOf(1));

        JLabel stateLabel = new JLabel("State:");
        stateLabel.setBounds(50, 470, 200, 30);
        stateLabel.setFont(new Font("Serif", Font.BOLD, 16));

        layeredPane.add(stateLabel, Integer.valueOf(1));

        stateField = new JTextField();
        stateField.setBounds(250, 470, 255, 30);
        layeredPane.add(stateField, Integer.valueOf(1));

        JLabel pinCodeLabel = new JLabel("Post Code:");
        pinCodeLabel.setBounds(50, 510, 200, 30);
        pinCodeLabel.setFont(new Font("Serif", Font.BOLD, 16));
        layeredPane.add(pinCodeLabel, Integer.valueOf(1));

        pinCodeField = new JTextField();
        pinCodeField.setBounds(250, 510, 255, 30);
        layeredPane.add(pinCodeField, Integer.valueOf(1));

        // Adding Signup button
        signupButton = new JButton("Next");
        signupButton.setBounds(700, 500, 150, 40);
        signupButton.setBackground(Color.BLACK);
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        layeredPane.add(signupButton, Integer.valueOf(1));

        backButton = new JButton("Back");
        backButton.setBounds(700, 400, 150, 40);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        layeredPane.add(backButton, Integer.valueOf(2));

        // Adding the layered pane to the frame
        setContentPane(layeredPane);
        setVisible(true);

        // Adding action listener to the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignup();
                dispose();
                new Signup2("");
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Splash();
                dispose();
            }
        });
    }

    private void handleSignup() {
        String name = nameField.getText();
        String fathersName = fathersNameField.getText();
        String dob = dayComboBox.getSelectedItem() + " " + monthComboBox.getSelectedItem() + " " + yearComboBox.getSelectedItem();
        String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "Other";
        String maritalStatus = married.isSelected() ? "Married" : unmarried.isSelected() ? "Unmarried" : "Other";
        String nid = nidField.getText();
        String address = addressArea.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String pinCode = pinCodeField.getText();

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/bank_management_system";
        String user = "root";
        String password = "root";  // Replace with your actual database password
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO signup_form1 (name, fathers_name, dob, gender, marital_status, nid, address, city, district, post_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, fathersName);
                stmt.setString(3, dob);
                stmt.setString(4, gender);
                stmt.setString(5, maritalStatus);
                stmt.setString(6, nid);
                stmt.setString(7, address);
                stmt.setString(8, city);
                stmt.setString(9, state);
                stmt.setString(10, pinCode);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during signup.");
        }
    }



    public static void main(String[] args) {
        new Signup();
    }
}
