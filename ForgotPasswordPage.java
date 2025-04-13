import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ForgotPasswordPage extends JFrame {
    private JTextField emailField;
    private JPasswordField newPasswordField;

    public ForgotPasswordPage() {
        setTitle("Forgot Password");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Set layout to BorderLayout for proper scaling

        // Background Image (scaled to the size of the screen)
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/posters/welcome_poster.jpg"));
        JLabel bgLabel = new JLabel(bgIcon) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Ensure background image stretches across the screen
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgLabel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering elements

        // Panel for the form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0, 0, 0, 180)); // semi-transparent black
        formPanel.setPreferredSize(new Dimension(500, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        JLabel title = new JLabel("Forgot Password");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(title, gbc);

        // Email Label
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(emailLabel, gbc);

        // Email Field
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("New Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(passLabel, gbc);

        // Password Field
        gbc.gridx = 1;
        newPasswordField = new JPasswordField(20);
        formPanel.add(newPasswordField, gbc);

        // Reset Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton resetButton = new JButton("Reset Password");
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetButton.addActionListener(e -> resetPassword());
        formPanel.add(resetButton, gbc);

        // Add form panel to the background label
        bgLabel.add(formPanel);

        add(bgLabel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void resetPassword() {
        String email = emailField.getText();
        String newPass = new String(newPasswordField.getPassword());

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/movie_ticket_booking", "root", "Vaibh@02");
            String sql = "UPDATE customer SET password=? WHERE email=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, newPass);
            pst.setString(2, email);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Email not found!");
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ForgotPasswordPage();
    }
}
