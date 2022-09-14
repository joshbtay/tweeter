package edu.byu.cs.tweeter.client.backgroundTask.handler;
import edu.byu.cs.tweeter.client.model.service.LoginService;

public class LogoutHandler extends SimpleNotificationHandler {
    public LogoutHandler(LoginService.LogoutObserver observer) {super(observer);}
    @Override
    protected String getFailedMessagePrefix() {
        return null;
    }
}