package StudentLibraryManagementSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class User {
	protected int userId;
    protected String name;
    protected String password;

    public User(int userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getPassword() { return password; }

    public abstract String getRole();

    public void logTransaction(String action) {
        String filename = "user_" + userId + ".txt";
        try (FileWriter fw = new FileWriter(filename, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            fw.write("[" + timestamp + "] " + action + "\n");
        } catch (IOException e) {
            System.out.println("Failed to log transaction for user " + userId);
        }
    }

}
