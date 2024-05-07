package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookManager {
    private List<Book> books;
    private Comparator<Book> currentComparator;

    public BookManager() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> filterAndSort(String sortBy, boolean ascending) {
        Comparator<Book> comparator;

        switch (sortBy) {
            case "title":
                comparator = Comparator.comparing(Book::getTitle);
                break;
            case "author":
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            case "rating":
                comparator = Comparator.comparingDouble(Book::getRating);
                break;
            default:
                comparator = Comparator.comparing(Book::getTitle);
        }

        if (currentComparator == comparator) {
            ascending = !ascending;
        }

        currentComparator = comparator;

        if (!ascending) {
            comparator = comparator.reversed();
        }

        Collections.sort(books, comparator);

        return books;
    }
}
