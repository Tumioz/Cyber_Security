import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class VulnerableApp {

    // Database connection string
    private static final String DB_URL = "jdbc:sqlite:school_project.db";

    public static void main(String[] args) {
        setupDatabase();
        runLoginPrompt();
    }

    private static void setupDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create the table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id_number TEXT PRIMARY KEY, " +
                    "name TEXT, " +
                    "surname TEXT, " +
                    "username TEXT, " +
                    "password TEXT" +
                    ")";
            stmt.execute(createTableSQL);

            // Insert a dummy user (ignores if already exists)
            String insertDummyUser = "INSERT OR IGNORE INTO users (id_number, name, surname, username, password) " +
                    "VALUES ('9901015000080', 'John', 'Doe', 'adam', 'SuperSecret123!')";
            stmt.execute(insertDummyUser);

        } catch (Exception e) {
            System.out.println("Database setup error: " + e.getMessage());
        }
    }

    private static void runLoginPrompt() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("=== School Portal Login ===");
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            // VULNERABILITY: Directly concatenating user input into the SQL string
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";

            System.out.println("\n[DEBUG] Executing Query: " + query + "\n");

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("Login Successful!");
                System.out.println("Welcome, " + rs.getString("name") + " " + rs.getString("surname"));
                System.out.println("Your ID Number is: " + rs.getString("id_number"));
            } else {
                System.out.println("Login Failed: Invalid username or password.");
            }

        } catch (Exception e) {
            System.out.println("Error executing login: " + e.getMessage());
        }
    }
}