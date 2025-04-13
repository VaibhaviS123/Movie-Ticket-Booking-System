import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.sql.*;

public class BookingPage extends JFrame {
    private Connection connection;
    private String movieName;

    public BookingPage(String movieName) {
        this.movieName = movieName;
        
        // Initialize the database connection
        initDatabaseConnection();
        
        setTitle("Movie Booking");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Book " + movieName, SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 36));
        header.setForeground(Color.RED);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // Movie details panel
        JPanel movieDetailsPanel = new JPanel();
        movieDetailsPanel.setBackground(Color.BLACK);
        movieDetailsPanel.setLayout(new BoxLayout(movieDetailsPanel, BoxLayout.Y_AXIS));
        movieDetailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Center the movie poster
        BufferedImage img = loadPosterImage(movieName);
        JLabel imgLabel;
        if (img != null) {
            Image scaled = img.getScaledInstance(400, 500, Image.SCALE_SMOOTH);
            imgLabel = new JLabel(new ImageIcon(scaled));
        } else {
            imgLabel = new JLabel("Poster not found", SwingConstants.CENTER);
            imgLabel.setForeground(Color.WHITE);
        }
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the poster
        movieDetailsPanel.add(imgLabel);

        // Movie Name
        JLabel title = new JLabel(movieName, SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title
        movieDetailsPanel.add(title);

        // Fetch movie details from the database
        String[] movieDetails = fetchMovieDetails(movieName);
        JLabel languageLabel = new JLabel("Language: " + movieDetails[0], SwingConstants.CENTER);
        languageLabel.setForeground(Color.WHITE);
        languageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        languageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the language label
        movieDetailsPanel.add(languageLabel);

        JLabel durationLabel = new JLabel("Duration: " + movieDetails[1], SwingConstants.CENTER);
        durationLabel.setForeground(Color.WHITE);
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        durationLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the duration label
        movieDetailsPanel.add(durationLabel);

        JLabel genreLabel = new JLabel("Genre: " + movieDetails[2], SwingConstants.CENTER);
        genreLabel.setForeground(Color.WHITE);
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the genre label
        movieDetailsPanel.add(genreLabel);

        // "Next" button
        JButton nextBtn = new JButton("Next");
        nextBtn.setBackground(Color.RED);
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFont(new Font("Arial", Font.BOLD, 18));
        nextBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the "Next" button
        nextBtn.setPreferredSize(new Dimension(200, 50)); // Set a fixed size for "Next" button
        nextBtn.addActionListener(e -> {
            new SeatSelectionPage(movieName).setVisible(true);
            dispose(); // close BookingPage
        });

        movieDetailsPanel.add(nextBtn);

        // "Back" button
        JButton backBtn = new JButton("Back");
        backBtn.setBackground(Color.RED);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 18));
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the "Back" button
        backBtn.setPreferredSize(new Dimension(200, 50)); // Set the same size for "Back" button
        backBtn.addActionListener(e -> {
            // Go back to the previous page (Dashboard or Welcome Page)
            new DashboardPage().setVisible(true); // Replace with your DashboardPage or WelcomePage
            dispose(); // close BookingPage
        });

        movieDetailsPanel.add(backBtn);

        // Add the movie details panel to the frame
        add(movieDetailsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private BufferedImage loadPosterImage(String name) {
        File dir = new File("src/posters");
        if (!dir.exists() || !dir.isDirectory()) return null;

        for (File f : dir.listFiles()) {
            String fname = f.getName();
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

    private String[] fetchMovieDetails(String movieName) {
        String[] movieDetails = new String[3]; // [language, duration, genre]

        try {
            String query = "SELECT language, duration, genre FROM movies WHERE movie_name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, movieName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                movieDetails[0] = rs.getString("language");
                movieDetails[1] = rs.getString("duration");
                movieDetails[2] = rs.getString("genre");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching movie details: " + e.getMessage());
        }

        return movieDetails;
    }

    private void initDatabaseConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/movie_ticket_booking"; // Your DB URL
            String user = "root"; // Your DB username
            String password = "Vaibh@02"; // Your DB password
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingPage("Dune"));
    }
}
