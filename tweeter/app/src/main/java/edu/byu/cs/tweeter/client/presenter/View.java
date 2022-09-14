package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.User;

public interface View {
    public void displayErrorMessage(String message);
    public void displayInfoMessage(String message);
}
