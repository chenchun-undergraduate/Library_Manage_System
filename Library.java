package StudentLibraryManagementSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
	private List<Book> books = new ArrayList<>();
    private Map<Integer, User> users = new HashMap<>();
    private final String FILE_PATH = "books.csv";
    private final String USER_FILE = "users.csv";
    private int nextBookId = 1;

    public void registerUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User login(int id, String password) {
        User u = users.get(id);
        if (u != null && u.getPassword().equals(password)) return u;
        return null;
    }
    
    public Boolean userExists(int id) {
    	return users.containsKey(id);
    }
    public void loadBooks() {
        books.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            int maxId = 0;

            
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Book b = Book.fromCSV(line);
                books.add(b);
                if (b.getId() > maxId) {
                    maxId = b.getId();
                }
            }

            nextBookId = maxId + 1; 
            System.out.println("Books loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }


    public void saveBooks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
        	pw.println("ID,Title,Author,Price,Category,IsBorrowed");
            for (Book b : books) {
                pw.println(b.toCSV());
            }
            System.out.println("Books saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public void addBook(String title, String author, double price, String category) {
        Book book = new Book(nextBookId++, title, author, price, category);
        books.add(book);
    }

    public void adminAddBook(Admin admin, String title, String author, double price, String category) {
        Book book = new Book(nextBookId++, title, author, price, category);
        books.add(book);
        admin.logAddBook(book);
    }

    public void removeBook(int id) {
        books.removeIf(b -> b.getId() == id);
    }

    public void adminRemoveBook(Admin admin, int id) {
        books.removeIf(b -> b.getId() == id);
        admin.logRemoveBook(id);
    }

    public void showAllBooks() {
        for (Book b : books) System.out.println(b);
    }

    public Book findBookById(int id) {
        for (Book b : books) if (b.getId() == id) return b;
        return null;
    }

    public void borrowBook(Student student, int bookId) {
        Book b = findBookById(bookId);
        if (b == null) {
            System.out.println("Book not found.");
        } else if (b.isBorrowed()) {
            System.out.println("Book is already borrowed.");
        } else if (!student.canBorrow()) {
            System.out.println("Borrow limit reached (max 3).");
        } else {
            b.borrow();
            student.borrowBook(b);
            System.out.println(student.getName() + " borrowed: " + b.getTitle());
        }
    }
    
    public void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USER_FILE))) {
        	pw.println("Type,ID,Name,Password");
            for (User user : users.values()) {
                String type = (user instanceof Admin) ? "admin" : "student";
                pw.println(String.format("%s,%d,%s,%s", type, user.getUserId(), user.getName(), user.getPassword()));
            }
            System.out.println("Users saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    
    public void loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                String type = parts[0];
                int id = Integer.parseInt(parts[1]);
                String name = parts[2];
                String pw = parts[3];

                if (type.equals("admin")) {
                    registerUser(new Admin(id, name, pw));
                } else {
                    registerUser(new Student(id, name, pw));
                }
            }
            System.out.println("Users loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
    
    public void returnBook(Student student, int bookId) {
        Book b = findBookById(bookId);
        if (b != null && b.isBorrowed()) {
            b.returnBook();
            student.returnBook(b);
            System.out.println(student.getName() + " returned: " + b.getTitle());
        } else {
            System.out.println("Book not borrowed or not found.");
        }
    }

}
