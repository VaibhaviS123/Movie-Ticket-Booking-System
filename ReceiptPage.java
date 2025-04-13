import javax.swing.*;
import java.awt.*;

public class ReceiptPage extends JFrame {

    public ReceiptPage(String movieName, String amountPaid) {
        setTitle("Ticket Booked");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.BLACK);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel bookedLabel = new JLabel("Ticket Booked Successfully");
        bookedLabel.setFont(new Font("Arial", Font.BOLD, 30));
        bookedLabel.setForeground(Color.GREEN);
        bookedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(bookedLabel);

        JLabel emailLabel = new JLabel("Please check your mail for more details");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(emailLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));

        Dimension buttonSize = new Dimension(180, 40);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setPreferredSize(buttonSize);
        exitBtn.addActionListener(e -> System.exit(0));

        JButton downloadBtn = new JButton("Download Ticket");
        downloadBtn.setFont(new Font("Arial", Font.BOLD, 16));
        downloadBtn.setBackground(Color.BLUE);
        downloadBtn.setForeground(Color.WHITE);
        downloadBtn.setPreferredSize(buttonSize);

        // ✅ Fix: This opens the TicketPage correctly
        downloadBtn.addActionListener(e -> {
            dispose(); // Optional: Close the current ReceiptPage
            new TicketPage(movieName, amountPaid,this);
        });

        buttonPanel.add(exitBtn);
        buttonPanel.add(downloadBtn);

        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(buttonPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(contentPanel, gbc);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ReceiptPage("Pathaan", "320");
        });
    }
}
