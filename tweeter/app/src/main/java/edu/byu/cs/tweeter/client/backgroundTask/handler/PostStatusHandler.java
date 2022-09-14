package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;

public class PostStatusHandler extends SimpleNotificationHandler{
    public PostStatusHandler(StatusService.PostStatusObserver observer) {super(observer);}
    @Override
    protected String getFailedMessagePrefix() {return "Failed to post status";}
}