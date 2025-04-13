import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaymentPage extends JFrame {

    private String movieName;
    private double totalAmount;
    private String amountPaid;

    public PaymentPage(String movieName, double totalAmount) {
        this.movieName = movieName;
        this.totalAmount = totalAmount;

        setTitle("Book Ticket");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Heading (No background color now)
        JLabel heading = new JLabel(" Book Ticket", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(heading, BorderLayout.NORTH);

        // Center Panel with dark background and layout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(20, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Payment Method Label
        JLabel paymentMethodLabel = new JLabel("Payment Method", SwingConstants.CENTER);
        paymentMethodLabel.setFont(new Font("Arial", Font.BOLD, 30));
        paymentMethodLabel.setForeground(Color.WHITE);
        paymentMethodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        paymentMethodLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // UPI Button
        // UPI Button
             JButton upiButton = new JButton("UPI");
             upiButton.setFont(new Font("Arial", Font.BOLD, 24));
             upiButton.setBackground(new Color(0, 128, 0));
               upiButton.setForeground(Color.WHITE);
          upiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                 upiButton.setMaximumSize(new Dimension(300, 50));
                    upiButton.setFocusPainted(false);
          upiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                upiButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
                upiButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
               // In your PaymentPage class
                 upiButton.addActionListener(e -> {
    // Assuming 'totalAmount' is a field in PaymentPage
          new UPIPage(movieName, String.valueOf(totalAmount)); // Pass totalAmount as String
});


        // Credit/Debit Card Button
        JButton cardButton = new JButton("Credit/Debit Card");
        cardButton.setFont(new Font("Arial", Font.BOLD, 24));
        cardButton.setBackground(new Color(0, 128, 0));
        cardButton.setForeground(Color.WHITE);
        cardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardButton.setMaximumSize(new Dimension(300, 50));
        cardButton.setFocusPainted(false);
        cardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        cardButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        cardButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Credit/Debit Card Payment Screen Opened!");
        });

        // Total Amount Display
        JLabel totalLabel = new JLabel("Total Amount: ₹" + totalAmount, SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 28));
        totalLabel.setForeground(Color.YELLOW);
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setMaximumSize(new Dimension(150, 50));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        cancelButton.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(this, "Payment Cancelled");
        });

        // Add all components to the center panel
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(paymentMethodLabel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(upiButton);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(cardButton);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(totalLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(cancelButton);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);
        getContentPane().setBackground(new Color(20, 20, 20));

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentPage("Dune", 499.00)); // Example with movie name and amount
    }
}
