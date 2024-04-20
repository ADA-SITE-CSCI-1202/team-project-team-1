package loginSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class IDandPassword {
    HashMap <String, String> loginInfo = new HashMap<>();

    public IDandPassword() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/farhadrahimov/Desktop/Team Project-1, Farhad/team-project-team-1/loginSystem/Book1.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] idPassword = line.split(",");
                if(idPassword.length == 2)
                    loginInfo.put(idPassword[0], idPassword[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected HashMap <String, String> getLoginInfo() {
        return loginInfo;
    }
}
