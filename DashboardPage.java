import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.sql.*;

public class DashboardPage extends JFrame {
    private JPanel movieListPanel;
    private Connection connection;

    public DashboardPage() {
        setTitle("Movie Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Book Movie", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 36));
        header.setForeground(Color.RED);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // Movie grid
        movieListPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        movieListPanel.setBackground(Color.BLACK);
        movieListPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Initialize database connection
        initDatabaseConnection();

        // Fetch movie data from the database
        fetchMoviesFromDatabase();

        JScrollPane scroll = new JScrollPane(movieListPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(50); // faster scrolling
        add(scroll, BorderLayout.CENTER);

        // Bottom panel with Back and Exit buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align buttons to the left
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Back button (to go back to the login page)
        JButton backBtn = new JButton("Back");
        backBtn.setBackground(Color.RED);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            new LoginPage();  // Navigate to LoginPage
            dispose();  // Close the DashboardPage
        });

        bottomPanel.add(backBtn);

        // Exit button (moved to bottom right corner)
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        exitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitBtn.addActionListener(e -> System.exit(0));

        bottomPanel.add(exitBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchMoviesFromDatabase() {
        try {
            String query = "SELECT movie_name FROM movies"; // Column is movie_name
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Fetch each movie from the database and create a card for it
            while (rs.next()) {
                String movieName = rs.getString("movie_name"); // Changed to match your column name

                // Remove the movies with no poster images (Pathaan, KGF, Inception, Drishyam, and Leo are removed)
                if (!movieName.equalsIgnoreCase("Pathaan") && !movieName.equalsIgnoreCase("KGF") && !movieName.equalsIgnoreCase("Inception") && !movieName.equalsIgnoreCase("Drishyam") && !movieName.equalsIgnoreCase("Leo")) {
                    // Use movie name to find the poster image
                    movieListPanel.add(createMovieCard(movieName));
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching movie data: " + e.getMessage());
        }
    }

    private JPanel createMovieCard(String movieName) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.DARK_GRAY);
        card.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        card.setPreferredSize(new Dimension(250, 400));

        // Load poster by scanning src/posters/
        BufferedImage img = loadPosterImage(movieName);
        JLabel imgLabel;
        if (img != null) {
            Image scaled = img.getScaledInstance(250, 300, Image.SCALE_SMOOTH);
            imgLabel = new JLabel(new ImageIcon(scaled));
        } else {
            imgLabel = new JLabel("Poster not found", SwingConstants.CENTER);
            imgLabel.setForeground(Color.WHITE);
            imgLabel.setPreferredSize(new Dimension(250, 300));
        }
        card.add(imgLabel, BorderLayout.CENTER);

        // Title
        JLabel title = new JLabel(movieName, SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        card.add(title, BorderLayout.NORTH);

        // Rating (alternating between 5 stars and 4 stars)
        String stars = (Math.random() > 0.5) ? "★★★★★" : "★★★★☆";
        JLabel ratingLabel = new JLabel("Rating: " + stars, SwingConstants.CENTER);
        ratingLabel.setForeground(Color.YELLOW);
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Book button
        JButton bookBtn = new JButton("Book Movie");
        bookBtn.setBackground(Color.RED);
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFont(new Font("Arial", Font.BOLD, 14));
        bookBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bookBtn.addActionListener(e -> openBookingPage(movieName));  // Navigate to BookingPage

        JPanel info = new JPanel(new GridLayout(2, 1, 5, 5));
        info.setBackground(Color.DARK_GRAY);
        info.add(bookBtn);
        info.add(ratingLabel);
        card.add(info, BorderLayout.SOUTH);

        return card;
    }

    private BufferedImage loadPosterImage(String name) {
        File dir = new File("src/posters");
        if (!dir.exists() || !dir.isDirectory()) return null;

        // Scan all files in src/posters/
        for (File f : dir.listFiles()) {
            String fname = f.getName();
            // Base name = text before first dot (or whole name if no dot)
            String base = fname.contains(".") ? fname.substring(0, fname.indexOf('.')) : fname;
            if (base.equalsIgnoreCase(name)) {
                try {
                    return ImageIO.read(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void openBookingPage(String movieName) {
        // Open the BookingPage for the selected movie
        new BookingPage(movieName).setVisible(true);  // Open BookingPage and pass movie name
        dispose();  // Close the current DashboardPage
    }

    private void initDatabaseConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/movie_ticket_booking"; // Your DB URL
            String user = "root"; // Your DB username
            String password = "Vaibh@02"; // Your DB password
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardPage::new);
    }
}
