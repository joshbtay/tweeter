package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.FollowHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetFollowersHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetFollowingHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.UnfollowHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.CountServiceObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.PagedServiceObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.ServiceObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleNotificationServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {


    public interface GetPageObserver extends PagedServiceObserver { }

    public interface CountObserver extends CountServiceObserver { }

    public interface IsFollowerObserver extends ServiceObserver {
        void handleSuccess(boolean isFollower);
    }

    public interface UnfollowObserver extends SimpleNotificationServiceObserver{ }
    public interface FollowObserver extends SimpleNotificationServiceObserver { }

    public void getFollowing(AuthToken authToken, User targetUser, int limit, User lastFollowee, PagedServiceObserver observer){
        GetFollowingTask getFollowingTask = new GetFollowingTask(authToken, targetUser, limit, lastFollowee, new GetFollowingHandler(observer));
        getFollowingTask.execute();
    }

    public void getFollowers(AuthToken authToken, User targetUser, int limit, User lastFollower, PagedServiceObserver observer){
        GetFollowersTask getFollowersTask = new GetFollowersTask(authToken, targetUser, limit, lastFollower, new GetFollowersHandler(observer));
        getFollowersTask.execute();
    }

    public void unfollow(AuthToken authToken, User user, UnfollowObserver observer){
        UnfollowTask unfollowTask = new UnfollowTask(authToken, user, new UnfollowHandler(observer));
        unfollowTask.execute();
    }
    public void follow(AuthToken authToken, User user, FollowObserver observer){
        FollowTask followTask = new FollowTask(authToken,
                user, new FollowHandler(observer));
        followTask.execute();
    }

    public void getCounts(AuthToken authToken, User currentUser,
                          CountObserver followersObserver, CountObserver followingObserver){
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(authToken, currentUser,
                new GetFollowersCountHandler(followersObserver));
        followersCountTask.execute();
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(authToken, currentUser,
                new GetFollowingCountHandler(followingObserver));
        followingCountTask.execute();
    }

    public void isFollower(AuthToken authToken, User currUser, User currentUser, IsFollowerObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(authToken, currUser,
                currentUser, new IsFollowerHandler(observer));
        isFollowerTask.execute();
    }

}
