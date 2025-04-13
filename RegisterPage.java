import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class RegisterPage extends JFrame {

    public RegisterPage() {
        setTitle("Register - Movie Ticket Booking System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Load background image
        URL imageUrl = getClass().getClassLoader().getResource("posters/welcome_poster.jpg");
        ImageIcon bgIcon = new ImageIcon(imageUrl);
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Transparent form panel - moved down
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(320, 100, 100, 100)); // further down & wider

        JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setBackground(new Color(0, 0, 0, 180)); // translucent black

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Sign Up");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        innerPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        innerPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20); // wider
        innerPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        innerPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        innerPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel cityLabel = new JLabel("City ID:");
        cityLabel.setForeground(Color.WHITE);
        innerPanel.add(cityLabel, gbc);
        gbc.gridx = 1;
        JTextField cityField = new JTextField(20);
        innerPanel.add(cityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        innerPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        JPasswordField passField = new JPasswordField(20);
        innerPanel.add(passField, gbc);

        // Register Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(50, 50, 50));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        innerPanel.add(registerBtn, gbc);

        // Button Action
        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            int cityId = Integer.parseInt(cityField.getText());
            String password = new String(passField.getPassword());

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                java.sql.Connection con = java.sql.DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/movie_ticket_booking", "root", "Vaibh@02");
                java.sql.PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO customer(customer_name, email, city_id, password) VALUES (?, ?, ?, ?)");
                pst.setString(1, name);
                pst.setString(2, email);
                pst.setInt(3, cityId);
                pst.setString(4, password);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                con.close();
                new DashboardPage();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        formPanel.add(innerPanel, new GridBagConstraints());
        background.add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterPage::new);
    }
}
