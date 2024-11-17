package Pages;

import Models.CSVMananger;
import Pages.loginSystem.LoginMain;

public class Main {
    public static void main(String[] args) {
        new LoginMain();
        
        CSVMananger.initialCreation();
    }
}
