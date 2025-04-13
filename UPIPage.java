import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UPIPage extends JFrame {
    private JPasswordField[] pinFields = new JPasswordField[6];
    private final String movieName;
    private final String amountPaid;


    public UPIPage(String movieName, String amountPaid) {
        this.movieName = movieName;
        this.amountPaid = amountPaid;
        

        setTitle("UPI Payment");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(100, 30, 20)); // Dark brown

        JPanel mainCard = new JPanel();
        mainCard.setLayout(new BoxLayout(mainCard, BoxLayout.Y_AXIS));
        mainCard.setBackground(Color.WHITE);
        mainCard.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainCard.setMaximumSize(new Dimension(500, 400));

        // UPI Info
        JLabel upiLogo = new JLabel("UPI");
        upiLogo.setFont(new Font("Arial", Font.BOLD, 30));
        upiLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        upiLogo.setForeground(new Color(90, 90, 90));
        mainCard.add(upiLogo);

        JLabel toLabel = new JLabel("To: CINEMANIA");
        toLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        toLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        toLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainCard.add(toLabel);

        JLabel amountLabel = new JLabel("Sending: ₹" + amountPaid);
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainCard.add(amountLabel);

        // Enter PIN Label
        JLabel pinLabel = new JLabel("ENTER 6-DIGIT UPI PIN");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainCard.add(pinLabel);

        // PIN Field Panel
        JPanel pinPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pinPanel.setBackground(Color.WHITE);
        for (int i = 0; i < 6; i++) {
            pinFields[i] = new JPasswordField(1);
            pinFields[i].setFont(new Font("Arial", Font.BOLD, 20));
            pinFields[i].setHorizontalAlignment(JTextField.CENTER);
            pinFields[i].setEchoChar('•');
            pinFields[i].setPreferredSize(new Dimension(40, 40));
            final int index = i;

            pinFields[i].addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if (Character.isDigit(e.getKeyChar())) {
                        if (index < 5) {
                            pinFields[index + 1].requestFocus();
                        }
                    } else {
                        e.consume();
                    }
                }
            });
            pinPanel.add(pinFields[i]);
        }
        mainCard.add(pinPanel);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        JButton payBtn = new JButton("Pay");
        payBtn.setFont(new Font("Arial", Font.BOLD, 16));
        payBtn.setBackground(new Color(100, 30, 20));
        payBtn.setForeground(Color.WHITE);
        payBtn.setFocusPainted(false);
        payBtn.setPreferredSize(new Dimension(100, 35));
        payBtn.addActionListener(e -> {
    StringBuilder pin = new StringBuilder();
    for (JPasswordField pf : pinFields) {
        pin.append(String.valueOf(pf.getPassword()));
    }

    if (pin.length() != 6) {
        JOptionPane.showMessageDialog(this, "Invalid UPI PIN. Please enter exactly 6 digits.");
    } else {
        dispose(); // Close UPIPage
        new ReceiptPage(movieName, amountPaid); // Open receipt with dynamic data
    }
});

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 16));
        cancelBtn.setBackground(Color.LIGHT_GRAY);
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setPreferredSize(new Dimension(100, 35));
        cancelBtn.addActionListener(e -> {
    dispose();
    new PaymentPage(movieName, Integer.parseInt(amountPaid));
       });

        buttonPanel.add(payBtn);
        buttonPanel.add(cancelBtn);

        mainCard.add(buttonPanel);

        // Wrap center in panel for centering
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(100, 30, 20));
        wrapper.add(mainCard);

        add(wrapper);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UPIPage("Sample Movie", "450")); // example test call
    }
}
