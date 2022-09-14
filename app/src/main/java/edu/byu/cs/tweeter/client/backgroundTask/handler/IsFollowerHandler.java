package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.FollowService;

public class IsFollowerHandler<T extends FollowService.IsFollowerObserver> extends BackgroundTaskHandler<T> {
    public IsFollowerHandler(T observer){
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle data) {
        observer.handleSuccess(data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY));
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to check if follower";
    }
}
