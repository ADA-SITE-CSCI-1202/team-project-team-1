package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersManager {
    private static final String CSV_FILE = "Data/accounts.csv";

    public UsersManager() {}

    public static List<User> readFromCsv() {
        List<User> users = new ArrayList<>();
       
        try(BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            reader.readLine();
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                
                String username = parts[0];
                String password = parts[1];
                
                users.add(new User(username, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static void addToCsv(User user) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            String line = String.format("%s,%s", user.getUsername(), user.getPassword());
            
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editUser(User newUser, String originalUsername) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                String username = parts[0].trim();

                if (username.equals(originalUsername)) {
                    lines.add(newUser.getUsername() + "," + newUser.getPassword());
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeUser(String usernameToRemove) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                String username = parts[0].trim();

                if (!username.equals(usernameToRemove)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(readFromCsv().toString());
    }
}
