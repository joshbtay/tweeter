package edu.byu.cs.tweeter.client.backgroundTask.observer;

public interface CountServiceObserver extends ServiceObserver{
    void handleSuccess(int count);
}
