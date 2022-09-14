package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.observer.PagedServiceObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends UserPresenter{

    public FollowingPresenter(PagedView<User> view, User targetUser){
        super(view, targetUser);
    }

    @Override
    protected void sendRequest() {
        new FollowService().getFollowing(authToken, targetUser, PAGE_SIZE, lastItem, getUserListObserver);
    }

}
