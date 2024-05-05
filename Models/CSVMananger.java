package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CSVMananger {
    private static final String CSV_FILE = "Data/brodsky.csv";
    
    public CSVMananger() {}

    public static List<Book> readFromCsv() {
        List<Book> books = new ArrayList<>();
       
        try(BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                String title = parts[0];
                String author = "N/A";
                
                if(parts[1] != null){
                    author = parts[1];
                }
                
                books.add(new Book(title, author));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static void addToCsv(Book book) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            String line = String.format("%s,%s", book.getTitle(), book.getAuthor());
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editInCsv(Book newBook, String originalName) {
        List<Book> books = readFromCsv();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Book book : books) {
                if (book.getTitle().equals(originalName)) {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                }

                String line = String.format("%s,%s", book.getTitle(), book.getAuthor());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  removeFromCsv(String bookName) {
        List<Book> books = readFromCsv();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Book book : books) {
                if (!book.getTitle().equals(bookName)) {
                    String line = String.format("%s,%s", book.getTitle(), book.getAuthor());
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
