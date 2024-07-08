package Components.Signup;

import JDBC.ConnectDB;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Signup2 extends JFrame implements ActionListener{

    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13;
    JButton b;
    JRadioButton r1, r2, r3, r4;
    JTextField t1, t2, t3;
    JComboBox c1, c2, c3, c4, c5;
    String formno;

    // BackgroundPanel inner class
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    Signup2(String formno) {

        // Load background image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("Images/signup.jpg"));
        Image backgroundImage = backgroundIcon.getImage();

        // Set BackgroundPanel as the content pane
        BackgroundPanel background = new BackgroundPanel(backgroundImage);
        setContentPane(background);
        background.setLayout(null);

        this.formno = formno;
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 2");

        l1 = new JLabel("Page 2: Additional Details");
        l1.setFont(new Font("Serif", Font.BOLD, 22));

        l2 = new JLabel("Religion:");
        l2.setFont(new Font("Serif", Font.BOLD, 18));

        l3 = new JLabel("Category:");
        l3.setFont(new Font("Serif", Font.BOLD, 18));

        l4 = new JLabel("Income:");
        l4.setFont(new Font("Serif", Font.BOLD, 18));

        l5 = new JLabel("Educational");
        l5.setFont(new Font("Serif", Font.BOLD, 18));

        l11 = new JLabel("Qualification:");
        l11.setFont(new Font("Serif", Font.BOLD, 18));

        l6 = new JLabel("Occupation:");
        l6.setFont(new Font("Serif", Font.BOLD, 18));

        l7 = new JLabel("Phone Number:");
        l7.setFont(new Font("Serif", Font.BOLD, 18));

        l8 = new JLabel("Holding Number:");
        l8.setFont(new Font("Serif", Font.BOLD, 18));

        l9 = new JLabel("Senior Citizen:");
        l9.setFont(new Font("Serif", Font.BOLD, 18));

        l10 = new JLabel("Existing Account:");
        l10.setFont(new Font("Serif", Font.BOLD, 18));

        l12 = new JLabel("Form No:");
        l12.setFont(new Font("Serif", Font.BOLD, 13));

        l13 = new JLabel(formno);
        l13.setFont(new Font("Serif", Font.BOLD, 13));

        b = new JButton("Next");
        b.setFont(new Font("Serif", Font.BOLD, 14));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 14));

        t2 = new JTextField();
        t2.setFont(new Font("Raleway", Font.BOLD, 14));

        r1 = new JRadioButton("Yes");
        r1.setFont(new Font("Raleway", Font.BOLD, 14));
        r1.setBackground(Color.WHITE);

        r2 = new JRadioButton("No");
        r2.setFont(new Font("Raleway", Font.BOLD, 14));
        r2.setBackground(Color.WHITE);

        r3 = new JRadioButton("Yes");
        r3.setFont(new Font("Raleway", Font.BOLD, 14));
        r3.setBackground(Color.WHITE);

        r4 = new JRadioButton("No");
        r4.setFont(new Font("Raleway", Font.BOLD, 14));
        r4.setBackground(Color.WHITE);

        String religion[] = {"Null","Hindu", "Muslim", "Sikh", "Christian", "Other"};
        c1 = new JComboBox(religion);
        c1.setBackground(Color.WHITE);
        c1.setFont(new Font("Raleway", Font.BOLD, 14));

        String category[] = {"General", "OBC", "SC", "ST", "Other"};
        c2 = new JComboBox(category);
        c2.setBackground(Color.WHITE);
        c2.setFont(new Font("Raleway", Font.BOLD, 14));

        String income[] = {"Null", "<1,50,000", "<2,50,000", "<5,00,000", "Upto 10,00,000", "Above 10,00,000"};
        c3 = new JComboBox(income);
        c3.setBackground(Color.WHITE);
        c3.setFont(new Font("Raleway", Font.BOLD, 14));

        String education[] = {"Non-Graduate", "Graduate", "Post-Graduate", "Doctorate", "Others"};
        c4 = new JComboBox(education);
        c4.setBackground(Color.WHITE);
        c4.setFont(new Font("Raleway", Font.BOLD, 14));

        String occupation[] = {"Salaried", "Self-Employed", "Business", "Student", "Retired", "Others"};
        c5 = new JComboBox(occupation);
        c5.setBackground(Color.WHITE);
        c5.setFont(new Font("Raleway", Font.BOLD, 14));

        l12.setBounds(700, 10, 60, 30);
        background.add(l12);

        l13.setBounds(760, 10, 60, 30);
        background.add(l13);

        l1.setBounds(280, 30, 600, 40);
        background.add(l1);

        l2.setBounds(100, 120, 100, 30);
        background.add(l2);

        c1.setBounds(350, 120, 320, 30);
        background.add(c1);

        l3.setBounds(100, 170, 100, 30);
        background.add(l3);

        c2.setBounds(350, 170, 320, 30);
        background.add(c2);

        l4.setBounds(100, 220, 100, 30);
        background.add(l4);

        c3.setBounds(350, 220, 320, 30);
        background.add(c3);

        l5.setBounds(100, 270, 150, 30);
        background.add(l5);

        c4.setBounds(350, 270, 320, 30);
        background.add(c4);

        l11.setBounds(100, 290, 150, 30);
        background.add(l11);

        l6.setBounds(100, 340, 150, 30);
        background.add(l6);

        c5.setBounds(350, 340, 320, 30);
        background.add(c5);

        l7.setBounds(100, 390, 150, 30);
        background.add(l7);

        t1.setBounds(350, 390, 320, 30);
        background.add(t1);

        l8.setBounds(100, 440, 180, 30);
        background.add(l8);

        t2.setBounds(350, 440, 320, 30);
        background.add(t2);

        l9.setBounds(100, 490, 150, 30);
        background.add(l9);

        r1.setBounds(350, 490, 100, 30);
        background.add(r1);

        r2.setBounds(460, 490, 100, 30);
        background.add(r2);

        l10.setBounds(100, 540, 180, 30);
        background.add(l10);

        r3.setBounds(350, 540, 100, 30);
        background.add(r3);

        r4.setBounds(460, 540, 100, 30);
        background.add(r4);

        b.setBounds(570, 640, 100, 30);
        background.add(b);

        b.addActionListener(this);

        setSize(850, 750);
        setLocation(500, 120);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String religion = (String) c1.getSelectedItem();
        String category = (String) c2.getSelectedItem();
        String income = (String) c3.getSelectedItem();
        String education = (String) c4.getSelectedItem();
        String occupation = (String) c5.getSelectedItem();

        String pan = t1.getText();
        String aadhar = t2.getText();

        String scitizen = "";
        if (r1.isSelected()) {
            scitizen = "Yes";
        } else if (r2.isSelected()) {
            scitizen = "No";
        }

        String eaccount = "";
        if (r3.isSelected()) {
            eaccount = "Yes";
        } else if (r4.isSelected()) {
            eaccount = "No";
        }

        String url = "jdbc:mysql://localhost:3306/bank_management_system";
        String user = "root";
        String password = "root";  // Replace with your actual database password
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO signup2 (religion, category, income, education, occupation, phone,holding, scitizen, eaccount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, religion);
                stmt.setString(2, category);
                stmt.setString(3, income);
                stmt.setString(4, education);
                stmt.setString(5, occupation);
                stmt.setString(6, pan);
                stmt.setString(7, aadhar);
                stmt.setString(8, scitizen);
                stmt.setString(9, eaccount);
                stmt.executeUpdate();

                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during signup.");
        }
        new Signup3();
        dispose();

    }

    public static void main(String[] args) {
        new Signup2("").setVisible(true);
    }
}
