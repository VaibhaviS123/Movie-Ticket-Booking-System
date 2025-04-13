import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {

    public LoginPage() {
        // Frame setup
        setTitle("Login - Movie Ticket Booking System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Load background image
        ImageIcon bgImage = new ImageIcon(getClass().getResource("/posters/welcome_poster.jpg"));
        JLabel backgroundLabel = new JLabel(bgImage);
        backgroundLabel.setLayout(new GridBagLayout());
        add(backgroundLabel);

        // Transparent panel for login form
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(0, 0, 0, 160));
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Login title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Email row
        JPanel emailRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailRow.setOpaque(false);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setPreferredSize(new Dimension(80, 30));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField emailField = new JTextField(20);
        emailRow.add(emailLabel);
        emailRow.add(emailField);

        // Password row
        JPanel passwordRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordRow.setOpaque(false);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setPreferredSize(new Dimension(80, 30));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JPasswordField passwordField = new JPasswordField(20);
        passwordRow.add(passwordLabel);
        passwordRow.add(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the email and password entered by the user
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Check credentials in the database
                if (validateLogin(email, password)) {
                    new DashboardPage(); // Open DashboardPage
                    dispose(); // Close the LoginPage
                } else {
                    // Show error message if email or password is incorrect
                    JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // "Forgot Password?" label (clickable)
        JLabel forgotPass = new JLabel("Forgot Password?");
        forgotPass.setForeground(Color.CYAN);
        forgotPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPass.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new ForgotPasswordPage(); // Open ForgotPasswordPage
                dispose();
            }
        });

        // "Don't have an account? Register now"
        JPanel registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        registerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        JLabel noAccountText = new JLabel("Don't have an account?");
        noAccountText.setForeground(Color.WHITE);
        noAccountText.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel registerNow = new JLabel(" Register now");
        registerNow.setForeground(Color.CYAN);
        registerNow.setFont(new Font("Arial", Font.BOLD, 14));
        registerNow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerNow.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new RegisterPage(); // Open RegisterPage
                dispose(); // Close LoginPage
            }
        });

        registerPanel.add(noAccountText);
        registerPanel.add(registerNow);

        // Add all components to login panel
        loginPanel.add(emailRow);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(passwordRow);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(forgotPass);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(registerPanel);

        // Center login panel vertically
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(300, 0, 0, 0); // Move panel slightly lower
        backgroundLabel.add(loginPanel, gbc);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(255, 100, 100));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 35));
    }

    // Method to validate login credentials from the database
    private boolean validateLogin(String email, String password) {
        boolean isValid = false;

        // Database credentials
        String url = "jdbc:mysql://localhost:3306/movie_ticket_booking"; // Your database URL
        String dbUser = "root"; // Your DB username
        String dbPassword = "Vaibh@02"; // Your DB password

        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);

            // Prepare SQL query to validate user
            String query = "SELECT * FROM customer WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Check if user exists
            if (rs.next()) {
                isValid = true; // Credentials are correct
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
