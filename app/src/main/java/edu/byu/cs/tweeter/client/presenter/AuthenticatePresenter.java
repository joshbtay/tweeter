package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticatePresenter extends Presenter<LoginView> {
    protected LoginService.LoginObserver loginObserver;
    public AuthenticatePresenter(LoginView view) {
        super(view);
        loginObserver = new LoginService.LoginObserver() {
            @Override
            public void handleSuccess(User user) {
                loginSucceeded(user);
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };
    }

    public void loginSucceeded(User loggedInUser) {
        view.displayInfoMessage("Hello " + loggedInUser.getFirstName());
        view.clearErrorMessage();
        view.navigateToUser(loggedInUser);
    }
}
