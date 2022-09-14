package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.observer.PagedServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class UserPresenter extends PagedPresenter<User> {

    public UserPresenter(PagedView view, User targetUser) {
        super(view, targetUser);
        getUserListObserver = new PagedServiceObserver<User>() {
            @Override
            public void handleSuccess(List<User> items, boolean hasMore) {
                addItems(items, hasMore);
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };
    }
    protected PagedServiceObserver<User> getUserListObserver;

}
