package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AuthenticatePresenter {

    public LoginPresenter(LoginView view) {
        super(view);
    }

    public void login(String alias, String password){
        String message = validateLogin(alias, password);
        if(message == null){
            view.displayInfoMessage("Logging in...");
            view.clearErrorMessage();
            new LoginService().login(alias, password, loginObserver);
        }
        else{
            view.displayErrorMessage(message);
        }
        // Send the login request.

    }

    private String validateLogin(String alias, String password) {
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }
        return null;
    }

}
