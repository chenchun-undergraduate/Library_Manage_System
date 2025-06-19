package StudentLibraryManagementSystem;

public class Admin extends User{
	public Admin(int id, String name, String password) {
        super(id, name, password);
    }

    public void logAddBook(Book book) {
        logTransaction("Added book: " + book.getTitle());
    }

    public void logRemoveBook(int bookId) {
        logTransaction("Removed book ID: " + bookId);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
    

}
