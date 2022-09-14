package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusPresenter extends PagedPresenter<Status>{


    public StatusPresenter(PagedView view, User targetUser) {
        super(view, targetUser);
        getStatusObserver = new StatusService.GetStatusObserver() {
            @Override
            public void handleSuccess(List items, boolean hasMore) {
                addItems(items, hasMore);
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };
    }
    private StatusService.GetStatusObserver getStatusObserver;

    @Override
    protected void sendRequest() {
        new StatusService().getFeed(authToken, targetUser, PAGE_SIZE, lastItem, getStatusObserver);
    }
}
