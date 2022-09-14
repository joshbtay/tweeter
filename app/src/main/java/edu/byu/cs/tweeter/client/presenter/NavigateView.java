package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.User;

public interface NavigateView extends View{
    public void navigateToUser(User user);
}
