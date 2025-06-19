package StudentLibraryManagementSystem;

import java.util.Scanner;

public class Main {
 private static final Scanner sc = new Scanner(System.in);
 private static final Library lib = new Library();

 public static void main(String[] args) {
     lib.loadBooks();
     lib.loadUsers();

     System.out.println("=== Welcome to the Library System ===");
     System.out.println("1. Register New Account");
     System.out.println("2. Login");
     System.out.print("Choose an option: ");
     String option = sc.nextLine();

     if (option.equals("1")) {
         registerNewAccount();
     }

     System.out.print("Enter User ID: ");
     int id = Integer.parseInt(sc.nextLine());
     System.out.print("Enter Password: ");
     String password = sc.nextLine();

     User user = lib.login(id, password);
     if (user == null) {
         System.out.println("Login failed. Please check your credentials.");
         return;
     }

     System.out.println("Login successful. Role: " + user.getRole());
     if (user instanceof Admin) {
         runAdminInterface((Admin) user);
     } else if (user instanceof Student) {
         runStudentInterface((Student) user);
     }

     lib.saveBooks();
     lib.saveUsers();
     System.out.println("Session ended. Goodbye!");
 }

 private static void registerNewAccount() {
     System.out.print("Register as (admin/student): ");
     String role = sc.nextLine().trim().toLowerCase();
     System.out.print("Choose a unique ID (number): ");
     int id = Integer.parseInt(sc.nextLine());

     while (lib.userExists(id)) {
         System.out.println("User ID already exists. Please try a different ID:");
         id = Integer.parseInt(sc.nextLine());
     }

     System.out.print("Enter your name: ");
     String name = sc.nextLine();
     System.out.print("Choose a password: ");
     String pw = sc.nextLine();

     if (role.equals("admin")) {
         lib.registerUser(new Admin(id, name, pw));
     } else {
         lib.registerUser(new Student(id, name, pw));
     }
     lib.saveUsers();

     System.out.println("Account registered successfully. You can now log in.");
 }

 private static void runAdminInterface(Admin admin) {
     while (true) {
         System.out.println("\n=== Admin Menu ===");
         System.out.println("1. Add Book");
         System.out.println("2. Remove Book");
         System.out.println("3. View All Books");
         System.out.println("4. Exit");
         System.out.print("Choose an option: ");
         String choice = sc.nextLine();

         switch (choice) {
             case "1":
                 System.out.print("Title: ");
                 String title = sc.nextLine();
                 System.out.print("Author: ");
                 String author = sc.nextLine();
                 System.out.print("Price: ");
                 double price = Double.parseDouble(sc.nextLine());
                 System.out.print("Category: ");
                 String category = sc.nextLine();
                 lib.adminAddBook(admin, title, author, price, category);
                 lib.saveBooks();
                 break;
             case "2":
                 lib.showAllBooks();
                 System.out.print("Enter Book ID to remove: ");
                 int removeId = Integer.parseInt(sc.nextLine());
                 lib.adminRemoveBook(admin, removeId);
                 break;
             case "3":
                 lib.showAllBooks();
                 break;
             case "4":
                 return;
             default:
                 System.out.println("Invalid choice.");
         }
     }
 }

 private static void runStudentInterface(Student student) {
     while (true) {
         System.out.println("\n=== Student Menu ===");
         System.out.println("1. View All Books");
         System.out.println("2. Borrow Book");
         System.out.println("3. Return Book");
         System.out.println("4. View My Borrowed Books");
         System.out.println("5. Exit");
         System.out.print("Choose an option: ");
         String choice = sc.nextLine();

         switch (choice) {
             case "1":
                 lib.showAllBooks();
                 break;
             case "2":
                 lib.showAllBooks();
                 System.out.print("Enter Book ID to borrow: ");
                 int borrowId = Integer.parseInt(sc.nextLine());
                 lib.borrowBook(student, borrowId);
                 lib.saveBooks();
                 break;
             case "3":
                 student.showBorrowedBooks();
                 System.out.print("Enter Book ID to return: ");
                 int returnId = Integer.parseInt(sc.nextLine());
                 lib.returnBook(student, returnId);
                 lib.saveBooks();
                 break;
             case "4":
                 student.showBorrowedBooks();
                 break;
             case "5":
                 return;
             default:
                 System.out.println("Invalid choice.");
         }
         lib.saveBooks();
     }
 }
}
