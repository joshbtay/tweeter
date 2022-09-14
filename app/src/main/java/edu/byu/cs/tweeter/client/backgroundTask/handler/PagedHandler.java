package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.PagedServiceObserver;

public abstract class PagedHandler<T extends PagedServiceObserver> extends BackgroundTaskHandler<T> {
    public PagedHandler(T observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(T observer, Bundle data) {
        List<T> items = (List<T>) data.getSerializable(PagedTask.ITEMS_KEY);
        boolean morePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);
        observer.handleSuccess(items, morePages);
    }
}
