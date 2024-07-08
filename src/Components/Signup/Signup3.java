package Components.Signup;

import Components.mailSender.SendEmail;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Signup3 extends JFrame {

    JLabel l1, l2, l3, l4, l5;
    JTextField t1;
    JCheckBox c1;
    JButton b;


    Signup3() {
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 3");

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Images/signup.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 750, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 850, 750);
        add(background);

        // Form heading
        l1 = new JLabel("Page 3: Email Verification");
        l1.setFont(new Font("Serif", Font.BOLD, 22));
        l1.setBounds(280, 30, 600, 40);
        background.add(l1);

        // Prompt text
        l2 = new JLabel("Provide your email to get account number:");
        l2.setFont(new Font("Serif", Font.BOLD, 18));
        l2.setBounds(200, 150, 500, 30);
        background.add(l2);

        // Email field
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 14));
        t1.setBounds(200, 200, 400, 30);
        background.add(t1);

        // Terms and conditions checkbox
        c1 = new JCheckBox("I accept all the terms and conditions.");
        c1.setFont(new Font("Raleway", Font.BOLD, 14));
        c1.setBackground(Color.WHITE);
        c1.setBounds(200, 250, 400, 30);
        background.add(c1);

        // Submit button
        b = new JButton("Submit");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setBounds(300, 300, 100, 30);
        background.add(b);

//        b.addActionListener(this);

        // Frame properties
        getContentPane().setBackground(Color.WHITE);
        setSize(850, 750);
        setLocation(500, 120);
        setVisible(true);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!c1.isSelected()) {
                    JOptionPane.showMessageDialog(null, "You must accept the terms and conditions.");
                } else if (t1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Email field cannot be empty.");
                } else {
                    Random ran = new Random();
                    int account = (ran.nextInt() % 90000) + 504093;
                    String accountNumber = "" + Math.abs(account);
                    String email = t1.getText();
                   new SendEmail(email, accountNumber);
                   new SetPin(accountNumber,email);
                   dispose();
                }
            }
        });
    }



    public static void main(String[] args) {
        new Signup3().setVisible(true);
    }
}
