package Pages.loginSystem;

public class LoginMain {
    
    public LoginMain(){
        IDandPassword iDandPassword = new IDandPassword();
        
        LoginPage loginPage = new LoginPage(iDandPassword.getLoginInfo());
    }
}
