package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetStatusHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.PostStatusHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.PagedServiceObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleNotificationServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {
    public interface GetStatusObserver extends PagedServiceObserver {}

    public interface PostStatusObserver extends SimpleNotificationServiceObserver { }

    public void getFeed(AuthToken authToken, User user, int limit, Status lastStatus, GetStatusObserver observer) {
        GetFeedTask getFeedTask = new GetFeedTask(authToken, user, limit, lastStatus, new GetStatusHandler(observer));
        getFeedTask.execute();
    }

    public void postStatus(AuthToken authToken, Status status, PostStatusObserver observer){
        PostStatusTask statusTask = new PostStatusTask(authToken,
                status, new PostStatusHandler(observer));
        statusTask.execute();
    }
}
