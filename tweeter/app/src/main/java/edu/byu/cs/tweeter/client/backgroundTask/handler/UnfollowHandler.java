package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;

public class UnfollowHandler extends SimpleNotificationHandler {
    public UnfollowHandler(FollowService.UnfollowObserver observer){
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() { return "Failed to unfollow";}
}
