package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginHandler<T extends LoginService.LoginObserver> extends BackgroundTaskHandler<T> {
    public LoginHandler(T observer){
        super(observer);
    }

    protected void handleSuccessMessage(T observer, Bundle data) {
        User loggedInUser = (User) data.getSerializable(LoginTask.USER_KEY);
        AuthToken authToken = (AuthToken) data.getSerializable(LoginTask.AUTH_TOKEN_KEY);
        Cache.getInstance().setCurrUser(loggedInUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        observer.handleSuccess(loggedInUser);
    }

    protected String getFailedMessagePrefix() { return "Failed to log in";}
}