package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVMananger {
    private static final String CSV_FILE = "Data/general.csv";
    private static final String Brodsky_FILE = "Data/brodsky.csv";

    public static void initialCreation() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Brodsky_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            
            writer.write("Title,Author,Reviews");
            writer.newLine();

            boolean isFirstLine = true;
        
            String line;
        
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
                String[] titles = parts[0].split(",\\s*");
                String author = (parts.length > 1 && !parts[1].isEmpty()) ? parts[1] : "Unknown";
        
                for (String title : titles) {
                    title = title.replaceAll("^\"|\"$", "");
                    
                    if (title.isEmpty()) {
                        title = "Unknown";
                    }

                    writer.write(title + "," + author + ",");
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
                String author = parts[1];
                List<Review> reviews = parseReviews(parts[2]);
                
                books.add(new GeneralBook(title, author, reviews));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    private static List<Review> parseReviews(String reviewString) { 
        List<Review> reviews = new ArrayList<>();

        // Remove leading and trailing brackets
        reviewString = reviewString.substring(1, reviewString.length() - 1);

        if (!reviewString.isEmpty()) {
            // Split review entries by ".(" and remove the closing parenthesis from the last entry
            String[] reviewEntries = reviewString.split("\\)\\.\\(");
            if (reviewEntries.length > 0) {
                reviewEntries[reviewEntries.length - 1] = reviewEntries[reviewEntries.length - 1].substring(0, reviewEntries[reviewEntries.length - 1].length() - 1);
            }
            
            for (String entry : reviewEntries) {
                String[] parts = entry.split("\\.");

                String user = parts[0];
                if (user.startsWith("(")) {
                    user = user.substring(1); // Remove leading bracket
                }

                String content = (parts.length > 1) ? parts[1] : "";
                int rating = (parts.length > 2 && !parts[2].isEmpty()) ? Integer.parseInt(parts[2]) : 0;

                reviews.add(new Review(user, content, rating));
            }
        }
        

        return reviews;
    }

    public static void addToCsv(Book book) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            String line = String.format("%s,%s,[]", book.getTitle(), book.getAuthor());
            
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addReviewToCsv(String title, Review review){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));
            List<String> lines = new ArrayList<>();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                if (parts[0].equals(title)) {
                    StringBuilder updatedLine = new StringBuilder(parts[0] + "," + parts[1] + ",");

                    String reviewsString = parts[2];

                    if (!reviewsString.equals("")) {
                        // If there are existing reviews, append the new review
                        updatedLine.append(reviewsString.substring(0, reviewsString.length() - 1)); // Remove the closing bracket
                        updatedLine.append(".(")
                                .append(review.getUser()).append(".")
                                .append(review.getContent()).append(".")
                                .append(review.getRating())
                                .append(")");
                    } else {
                        // If there are no existing reviews, create a new list of reviews
                        updatedLine.append("(")
                                .append(review.getUser()).append(".")
                                .append(review.getContent()).append(".")
                                .append(review.getRating())
                                .append(")");
                    }
                    line = updatedLine.toString();
                }
                lines.add(line);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE));
            for (String l : lines) {
                writer.write(l + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editInCsv(Book newBook, String originalName) {
        List<GeneralBook> books = readFromCsv();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            writer.write("Title,Author,Reviews");
            writer.newLine();
            
            for (GeneralBook book : books) {
                if (book.getTitle().equals(originalName)) {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    // Update reviews if necessary
                }
    
                StringBuilder lineBuilder = new StringBuilder();
                lineBuilder.append(book.getTitle()).append(",");
                lineBuilder.append(book.getAuthor()).append(",");
                
                // Append reviews
                if (book instanceof GeneralBook) {
                    GeneralBook generalBook = (GeneralBook) book;
                    List<Review> reviews = generalBook.getReviews();
                    if (reviews != null && !reviews.isEmpty()) {
                        lineBuilder.append("[");
                        for (Review review : reviews) {
                            lineBuilder.append("(")
                                       .append(review.getUser()).append(".")
                                       .append(review.getContent()).append(".")
                                       .append(review.getRating())
                                       .append(").");
                        }
                        lineBuilder.deleteCharAt(lineBuilder.length() - 1); // Remove the trailing period
                        lineBuilder.append("]");
                    }
                }
                writer.write(lineBuilder.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromCsv(String bookName) {
        List<String> linesToRemove = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(bookName)) {
                    linesToRemove.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        allLines.removeAll(linesToRemove);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (String line : allLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(readFromCsv());
        
    }
    
}
