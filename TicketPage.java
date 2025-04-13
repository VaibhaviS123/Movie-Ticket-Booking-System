import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketPage extends JFrame {

    private JFrame paymentPage; // Reference to the PaymentPage

    public TicketPage(String movieName, String amountPaid, JFrame paymentPage) {
        this.paymentPage = paymentPage; // Store the reference

        setTitle("Ticket Preview");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new GridBagLayout()); // For center alignment
        getContentPane().setBackground(Color.BLACK); // Black background

        // Get current date
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        // Ticket panel (white "ticket" on black background)
        JPanel ticketPanel = new JPanel();
        ticketPanel.setBackground(Color.WHITE);
        ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.Y_AXIS));
        ticketPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel titleLabel = new JLabel("✔ Ticket Booked Successfully");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(34, 139, 34));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel movieLabel = new JLabel("Movie: " + movieName);
        movieLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        movieLabel.setForeground(Color.BLACK);
        movieLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel amountLabel = new JLabel("Amount Paid: ₹" + amountPaid);
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        amountLabel.setForeground(Color.BLACK);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dateLabel = new JLabel("Date: " + date);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        dateLabel.setForeground(Color.BLACK);
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension btnSize = new Dimension(200, 40);

        JButton downloadBtn = new JButton("Download Ticket");
        downloadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        downloadBtn.setBackground(Color.BLUE);
        downloadBtn.setForeground(Color.WHITE);
        downloadBtn.setFocusPainted(false);
        downloadBtn.setPreferredSize(btnSize);
        downloadBtn.setMaximumSize(btnSize);

        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setBackground(Color.DARK_GRAY);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setPreferredSize(btnSize);
        backBtn.setMaximumSize(btnSize);

        // Button Actions
        downloadBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Download Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the page after OK on dialog
            if (paymentPage != null) {
                paymentPage.dispose(); // Close PaymentPage as well
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new ReceiptPage(movieName, amountPaid);
        });

        // Add components to panel
        ticketPanel.add(titleLabel);
        ticketPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ticketPanel.add(movieLabel);
        ticketPanel.add(amountLabel);
        ticketPanel.add(dateLabel);
        ticketPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ticketPanel.add(downloadBtn);
        ticketPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        ticketPanel.add(backBtn);

        // Center the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(ticketPanel, gbc);

        setVisible(true);
    }

    // For testing independently
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicketPage("Pathaan", "320", null));
    }
}
