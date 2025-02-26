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
        UsersManager um = new UsersManager();

        if (!um.readFromCsv().isEmpty()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(Brodsky_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            
            writer.write("ID,Title,Author,Reviews");
            writer.newLine();

            boolean isFirstLine = true;
        
            String line;
            int id = 0;
        
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

                    writer.write(id++ + "," + title + "," + author + ",");
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
                
                Integer id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                List<Review> reviews = parseReviews(parts[3]);
                
                books.add(new GeneralBook(id, title, author, reviews));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    private static List<Review> parseReviews(String reviewString) { 
        List<Review> reviews = new ArrayList<>();

        if (reviewString.isEmpty()) {
            return reviews;
        }
        
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

        return reviews;
    }

    public static void addToCsv(GeneralBook book) {
        // Check if the book already exists
        

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            String line = String.format("%d,%s,%s,", book.getId(), book.getTitle(), book.getAuthor());
            
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean bookExists(Book book) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(book.getTitle()) && parts[1].equals(book.getAuthor())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    } 

    public static void addReviewToCsv(Integer id, Review review){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));
            List<String> lines = new ArrayList<>();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                if (parts[0].equals(String.valueOf(id))) {
                    StringBuilder updatedLine = new StringBuilder(parts[0] + "," + parts[1] + "," + parts[2] + ",");

                    String reviewsString = parts[3];

                    if (!reviewsString.equals("")) {
                        // If there are existing reviews, append the new review
                        updatedLine.append(reviewsString.substring(0, reviewsString.length()));
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

    public static void editInCsv(GeneralBook newBook, Integer id) {
        List<GeneralBook> books = readFromCsv();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            writer.write("Title, Author,Reviews");
            writer.newLine();
            
            for (GeneralBook book : books) {
                if (book.getId().equals(id)) {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    book.setReviews(newBook.getReviews());
                    // Update reviews if necessary
                }
    
                StringBuilder lineBuilder = new StringBuilder();
                lineBuilder.append(book.getId()).append(",");
                lineBuilder.append(book.getTitle()).append(",");
                lineBuilder.append(book.getAuthor()).append(",");
                
                // Append reviews
                if (book instanceof GeneralBook) {
                    GeneralBook generalBook = (GeneralBook) book;
                    List<Review> reviews = generalBook.getReviews();
                    if (reviews != null && !reviews.isEmpty()) {
                        for (Review review : reviews) {
                            lineBuilder.append("(")
                                       .append(review.getUser()).append(".")
                                       .append(review.getContent()).append(".")
                                       .append(review.getRating())
                                       .append(").");
                        }
                        lineBuilder.deleteCharAt(lineBuilder.length() - 1);
                    }
                }
                writer.write(lineBuilder.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromCsv(Integer id) {
        List<String> linesToRemove = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(String.valueOf(id))) {
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
        initialCreation();
    }
    
}
