package edu.byu.cs.tweeter.client.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.StatusService;

public class GetStatusHandler extends PagedHandler {
    public GetStatusHandler(StatusService.GetStatusObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get feed";
    }
}
