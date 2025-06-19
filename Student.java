package StudentLibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{
	 private List<Book> borrowedBooks = new ArrayList<>();

	    public Student(int id, String name, String password) {
	        super(id, name, password);
	    }

	    public boolean canBorrow() {
	        return borrowedBooks.size() < 3;
	    }

	    public void borrowBook(Book book) {
	        borrowedBooks.add(book);
	        logTransaction("Borrowed book: " + book.getTitle());
	    }

	    public void returnBook(Book book) {
	        borrowedBooks.remove(book);
	        logTransaction("Returned book: " + book.getTitle());
	    }

	    public List<Book> getBorrowedBooks() {
	        return borrowedBooks;
	    }

	    public void showBorrowedBooks() {
	        System.out.println("Books borrowed by " + name + ":");
	        for (Book b : borrowedBooks) System.out.println("  " + b);
	    }

	    @Override
	    public String getRole() {
	        return "Student";
	    }

}
