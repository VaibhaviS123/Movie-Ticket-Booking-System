import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame {

    public WelcomePage() {
        setTitle("Welcome - Movie Ticket Booking System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen

        // Load and set background image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/posters/welcome_poster.jpg")); // Ensure this path is correct
        Image bgImage = bgIcon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        bgIcon = new ImageIcon(bgImage);
        JLabel background = new JLabel(bgIcon);
        setContentPane(background);
        background.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);

        // Smaller welcome label with horizontal text
        JLabel title = new JLabel("🎬 Welcome to Movie Ticket Booking System 🎟️", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30)); // Smaller font size
        title.setForeground(new Color(255, 215, 0)); // Gold color for text
        title.setOpaque(false);
        gbc.gridy = 0;
        background.add(title, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.setOpaque(false);

        JButton loginBtn = createStyledButton("Login");
        JButton registerBtn = createStyledButton("Register");

        loginBtn.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        registerBtn.addActionListener(e -> {
            new RegisterPage().setVisible(true);
            dispose();
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);

        gbc.gridy = 1;
        background.add(buttonPanel, gbc);

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 22));
        button.setBackground(new Color(0, 0, 0, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setPreferredSize(new Dimension(180, 50));
        return button;
    }

    public static void main(String[] args) {
        new WelcomePage();
    }
}
