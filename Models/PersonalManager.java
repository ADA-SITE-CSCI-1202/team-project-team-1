package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonalManager {
    private static final String CSV_FOLDER = "Data/UserData/";

    public static void createUserCsv(String username) {
        String csvFileName = CSV_FOLDER + username + ".csv";
        File csvFile = new File(csvFileName);

        File folder = new File(CSV_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("Title,Author,Reviews,Status,TimeSpent,StartDate,EndDate,UserRating,UserReview");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<PersonalBook> readFromCsv(String username) {
        List<PersonalBook> personalBooks = new ArrayList<>();

        String csvFile = CSV_FOLDER + username + ".csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            reader.readLine(); // Skip header line

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                String title = parts[0];
                String author = parts[1];
                List<Review> reviews = parseReviews(parts[2]);
                String status = !parts[3].isEmpty() ? parts[3] : "Not Started";
                int timeSpent = Integer.parseInt(parts[4]);
                String startDate = parts[5];
                String endDate = parts[6];
                int userRating = !parts[7].isEmpty() ? Integer.parseInt(parts[7]) : 0;
                String userReview = !parts[8].isEmpty() ? parts[8] : "No Review";

                personalBooks.add(new PersonalBook(title, author, reviews, status, timeSpent, startDate, endDate, userRating, userReview));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return personalBooks;
    }

    private static List<Review> parseReviews(String reviewString) {
        List<Review> reviews = new ArrayList<>();

        // Remove leading and trailing brackets
        reviewString = reviewString.substring(1, reviewString.length() - 1);

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

    public static void addPersonalBookToCsv(GeneralBook book, String username) {
        String csvFileName = CSV_FOLDER + username + ".csv";

        if (personalBookExists(book.getTitle(), username)) {
            System.out.println("The book already exists for this user.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName, true))) {
            String line = String.format("%s,%s,%s,%s,%d,%s,%s,%d,%s",
                    book.getTitle(), book.getAuthor(), formatReviews(book.getReviews()), "Not Started", 0,
                    "", "", 0, "No Review");

            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPersonalBookToCsv(PersonalBook book, String username) {
        String csvFileName = CSV_FOLDER + username + ".csv";
        
        if (personalBookExists(book.getTitle(), username)) {
            System.out.println("The book already exists for this user.");
            return;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName, true))) {
            String line = String.format("%s,%s,%s,%s,%d,%s,%s,%d,%s",
            book.getTitle(), book.getAuthor(), formatReviews(book.getReviews()), "Not Started", book.getTimeSpent(),
            book.getStartDate(), book.getEndDate(), book.getUserRating(), !book.getUserReview().isEmpty() ? book.getUserReview() : "No Review");
            
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean personalBookExists(String bookTitle, String username) {
        String csvFileName = CSV_FOLDER + username + ".csv";
    
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
            reader.readLine(); // Skip header line
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(bookTitle)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    public static void removeBookFromCsv(String bookName, String username) {
        String csvFileName = CSV_FOLDER + username + ".csv";
        
        List<String> linesToRemove = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
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
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        allLines.removeAll(linesToRemove);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName))) {
            for (String line : allLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatReviews(List<Review> reviews) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Review review : reviews) {
            sb.append("(").append(review.getUser()).append(".").append(review.getContent()).append(".").append(review.getRating()).append(")");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        removeBookFromCsv("Mur", "Murad");
    }
}
