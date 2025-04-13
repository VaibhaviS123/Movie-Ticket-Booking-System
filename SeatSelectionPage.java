import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class SeatSelectionPage extends JFrame {
    private Set<JButton> selectedSeats = new HashSet<>();
    private JLabel totalAmountLabel;
    private final int seatPrice = 250; // ✅ 250 per seat

    public SeatSelectionPage(String movieName) {
        setTitle("Seat Selection - " + movieName);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 20)); // Dark theme

        // Top: Title
        JLabel screenLabel = new JLabel("🎫 SELECT YOUR SEAT - " + movieName.toUpperCase(), SwingConstants.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 32));
        screenLabel.setForeground(Color.WHITE);
        screenLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(screenLabel, BorderLayout.NORTH);

        // Center: Seats
        JPanel seatPanel = new JPanel(new GridLayout(8, 10, 15, 15));
        seatPanel.setBackground(new Color(30, 30, 30));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        char row = 'A';
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 10; j++) {
                String seatLabel = row + String.valueOf(j);
                JButton seat = new JButton(seatLabel);
                seat.setFocusPainted(false);
                seat.setBackground(Color.GREEN); // Available
                seat.setForeground(Color.BLACK);

                // Simulate some unavailable seats
                if ((i * 10 + j) % 7 == 0) {
                    seat.setBackground(Color.GRAY);
                    seat.setEnabled(false);
                } else {
                    seat.addActionListener(e -> {
                        if (selectedSeats.contains(seat)) {
                            seat.setBackground(Color.GREEN);
                            selectedSeats.remove(seat);
                        } else {
                            seat.setBackground(Color.YELLOW);
                            selectedSeats.add(seat);
                        }
                        updateTotalAmount();
                    });
                }

                seatPanel.add(seat);
            }
            row++;
        }

        add(seatPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(20, 20, 20));

        // Legends + Total
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        legendPanel.setBackground(new Color(20, 20, 20));
        legendPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        legendPanel.add(createLegend(Color.GREEN, "Available"));
        legendPanel.add(createLegend(Color.GRAY, "Unavailable"));
        legendPanel.add(createLegend(Color.YELLOW, "Selected"));

        // Total
        totalAmountLabel = new JLabel("Total: ₹0");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalAmountLabel.setForeground(Color.WHITE);
        legendPanel.add(totalAmountLabel);

        // Buttons
        JButton payButton = new JButton("Proceed to Payment");
        payButton.setFont(new Font("Arial", Font.BOLD, 20));
        payButton.setBackground(Color.RED);
        payButton.setForeground(Color.WHITE);
        payButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        payButton.setPreferredSize(new Dimension(250, 50));

     payButton.addActionListener(e -> {
    if (selectedSeats.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select at least one seat.");
    } else {
        double totalAmount = selectedSeats.size() * seatPrice; // Calculate total amount
        dispose();
        new PaymentPage(movieName,totalAmount); // Open PaymentPage with totalAmount
    }
});

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(250, 50));
        backButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new BookingPage(movieName));
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(20, 20, 20));
        buttonPanel.add(backButton);
        buttonPanel.add(payButton);

        bottomPanel.add(legendPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel createLegend(Color color, String label) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(20, 20, 20));

        JButton colorBox = new JButton();
        colorBox.setEnabled(false);
        colorBox.setPreferredSize(new Dimension(20, 20));
        colorBox.setBackground(color);

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 15));

        panel.add(colorBox);
        panel.add(lbl);
        return panel;
    }

    private void updateTotalAmount() {
        int total = selectedSeats.size() * seatPrice;
        totalAmountLabel.setText("Total: ₹" + total);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeatSelectionPage("Dune")); // 👈 Sample test
    }
}
