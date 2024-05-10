package Pages;

import Models.CSVMananger;
import Pages.loginSystem.LoginMain;

public class Main {
    public static void main(String[] args) {
        LoginMain loginMain = new LoginMain();

        CSVMananger manager = new CSVMananger();
        manager.initialCreation();
    }
}
