package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends AuthenticatePresenter{

    public RegisterPresenter(LoginView view){
        super(view);
    }

    public void register(String firstName, String lastName, String alias,
                         String password, String img){
        String message = validateRegistration(firstName, lastName, alias, password, img);
        if(message == null){
            view.displayInfoMessage("Registering...");
            view.clearErrorMessage();
            new LoginService().register(firstName, lastName, alias, password, img, loginObserver);
        }
        else{
            view.displayErrorMessage(message);
        }
    }

    private String validateRegistration(String firstName, String lastName, String alias,
                                        String password, String img){
        if (firstName.length() == 0) {
            return "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Last Name cannot be empty.";
        }
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }

        if (img == null) {
            return "Profile image must be uploaded.";
        }
        return null;
    }
}
