package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.LoginHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.ServiceObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleNotificationServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginService {
    public interface LoginObserver extends ServiceObserver {
        void handleSuccess(User user);
    }

    public interface LogoutObserver extends SimpleNotificationServiceObserver { }

    public void login(String alias, String password, LoginObserver observer){
        LoginTask loginTask = new LoginTask(alias, password, new LoginHandler(observer));
        loginTask.execute();
    }

    public void register(String firstName, String lastName, String alias,
                         String password, String img, LoginObserver observer){
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, img, new LoginHandler(observer));
        registerTask.execute();
    }

    public void logout(AuthToken authToken, LogoutObserver observer){
        LogoutTask logoutTask = new LogoutTask(authToken, new LogoutHandler(observer));
        logoutTask.execute();
    }

}
