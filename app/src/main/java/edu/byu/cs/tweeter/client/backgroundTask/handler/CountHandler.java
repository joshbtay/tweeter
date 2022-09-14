package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.CountServiceObserver;

public abstract class CountHandler<T extends CountServiceObserver> extends BackgroundTaskHandler<T>{
    public CountHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle data) {
        int count = data.getInt(GetFollowersCountTask.COUNT_KEY);
        observer.handleSuccess(count);
    }
}
