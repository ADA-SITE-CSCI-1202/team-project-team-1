package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CSVMananger {
    private static final String CSV_FILE = "Data/general.csv";
    private static final String Brodsky_FILE = "Data/brodsky.csv";
    
    public CSVMananger() {}

    public static void initialCreation() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Brodsky_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                String[] titles = parts[0].split(",\\s*");
                String author = (parts.length > 1 && !parts[1].isEmpty()) ? parts[1] : "N/A";

                for (String title : titles) {
                    title = title.replaceAll("^\"|\"$", "");
                    writer.write(title + "," + author);
                    writer.newLine();
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<GeneralBook> readFromCsv() {
        List<GeneralBook> books = new ArrayList<>();
       
        try(BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            reader.readLine();
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                
                String title = parts[0];
                String author = (parts.length > 1 && !parts[1].isEmpty()) ? parts[1] : "N/A";
                
                books.add(new GeneralBook(title, author));
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
        List<GeneralBook> books = readFromCsv();

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

    public static void removeFromCsv(String bookName) {
        List<GeneralBook> books = readFromCsv();

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

    public static void main(String[] args) {
        System.out.println(readFromCsv().toString());
    }
    
}
