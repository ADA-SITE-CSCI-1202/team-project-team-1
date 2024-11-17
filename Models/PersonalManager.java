package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
            writer.write("BookID,Status,TimeSpent,StartDate,EndDate,UserRating,UserReview");
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

                Integer id = Integer.parseInt(parts[0]);
                String status = !parts[1].isEmpty() ? parts[1] : "Not Started";
                int timeSpent = Integer.parseInt(parts[2]);
                String startDate = parts[3];
                String endDate = parts[4];
                int userRating = !parts[5].isEmpty() ? Integer.parseInt(parts[5]) : 0;
                String userReview = !parts[6].isEmpty() ? parts[6] : "";

                personalBooks.add(new PersonalBook(id, status, timeSpent, startDate, endDate, userRating, userReview));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return personalBooks;
    }

    private static List<Review> parseReviews(String reviewString) {
        List<Review> reviews = new ArrayList<>();

        if (reviewString.isEmpty()){
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

    // public static void addPersonalBookToCsv(GeneralBook book, String username) {
    //     String csvFileName = CSV_FOLDER + username + ".csv";

    //     if (personalBookExists(book.getId(), username)) {
    //         JOptionPane.showMessageDialog(null, "The book already exists for this user.", "Warning", JOptionPane.ERROR_MESSAGE);
    //         return;
    //     }

    //     try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName, true))) {
    //         String line = String.format("%d,%s,%d,%s,%s,%d,%s",
    //                 book.getId(), "Not Started", 0,
    //                 "", "", 0, "");

    //         writer.write(line);
    //         writer.newLine();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public static void addPersonalBookToCsv(PersonalBook book, String username, Review review) {
        String csvFileName = CSV_FOLDER + username + ".csv";
        
        if (personalBookExists(book.getId(), username)) {
            JOptionPane.showMessageDialog(null, "The book already exists for this user.", "Warning", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println("ha?");
        CSVMananger.addReviewToCsv(book.getId(), review);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName, true))) {
            String line = String.format("%d,%s,%d,%s,%s,%d,%s",
            book.getId(), "Not Started", book.getTimeSpent(),
            book.getStartDate(), book.getEndDate(), book.getUserRating(), !book.getUserReview().isEmpty() ? book.getUserReview() : "");
            
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean personalBookExists(Integer id, String username) {
        String csvFileName = CSV_FOLDER + username + ".csv";
    
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
            reader.readLine(); // Skip header line
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(String.valueOf(id))) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    public static void removeBookFromCsv(Integer id, String username) {
        String csvFileName = CSV_FOLDER + username + ".csv";
        
        List<String> linesToRemove = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
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

    public static void changeRatingReview(Integer id, String username, Integer rating, String content, String which){
        String csvFileName = CSV_FOLDER + username + ".csv";
        System.out.println(csvFileName);

        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = {"-1","Status","TimeSpent","StartDate","EndDate","-1",""};
                String[] lines = line.split(","); //sometimes when you try to change rating but there is no review, it clears the comma in csv and takes parts.length as 6.  
                for (int i = 0; i < lines.length; i++) {// but in that way it makes sure that parts.length is 7
                    parts[i] = lines[i];
                }
                if (parts.length >= 1 && parts[0].equals(String.valueOf(id))) {
                    if (which.equals("rating")){
                        parts[5] = String.valueOf(rating);
                    }
                    else{
                        parts[6] = content;
                    }  
                    
                }
                System.out.println();
                updatedLines.add(String.join(",", parts));

                GeneralBook gb = new GeneralBook(id);
                List<Review> rws = gb.getReviews();
                for (Review review : rws) {
                    if (review.getUser().equals(username)) {
                        if (which.equals("rating")) {
                            review.setRating(rating);
                        }
                        else{
                            review.setContent(content);
                        }
                            
                    }
                }
                CSVMananger.editInCsv(gb, gb.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private static String formatReviews(List<Review> reviews) {
        StringBuilder sb = new StringBuilder();
        for (Review review : reviews) {
            sb.append("(").append(review.getUser()).append(".").append(review.getContent()).append(".").append(review.getRating()).append(")");
        }
        return sb.toString();
    }
}
