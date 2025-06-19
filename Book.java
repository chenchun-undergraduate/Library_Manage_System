package StudentLibraryManagementSystem;

public class Book {
	 private int id;
	    private String title;
	    private String author;
	    private double price;
	    private String category;
	    private boolean isBorrowed;

	    public Book(int id, String title, String author, double price, String category) {
	        this.id = id;
	        this.title = title;
	        this.author = author;
	        this.price = price;
	        this.category = category;
	        this.isBorrowed = false;
	    }

	    public int getId() { return id; }
	    public String getTitle() { return title; }
	    public String getCategory() { return category; }
	    public boolean isBorrowed() { return isBorrowed; }

	    public void borrow() { isBorrowed = true; }
	    public void returnBook() { isBorrowed = false; }

	    public String toCSV() {
	        return String.format("%d,%s,%s,%.2f,%s,%s",
	                id, title, author, price, category, isBorrowed ? "yes" : "no");
	    }

	    public static Book fromCSV(String line) {
	        String[] parts = line.split(",", -1);
	        Book book = new Book(
	            Integer.parseInt(parts[0]),
	            parts[1],
	            parts[2],
	            Double.parseDouble(parts[3]),
	            parts[4]
	        );
	        if (parts[5].equalsIgnoreCase("yes")) {
	            book.borrow();
	        }
	        return book;
	    }

	    @Override
	    public String toString() {
	        return String.format("Book[ID=%d, Title='%s', Author='%s', Price=%.2f, Category=%s, %s]",
	                id, title, author, price, category, isBorrowed ? "Borrowed" : "Available");
	    }

}
