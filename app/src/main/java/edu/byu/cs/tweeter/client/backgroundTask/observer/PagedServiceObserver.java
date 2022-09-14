package edu.byu.cs.tweeter.client.backgroundTask.observer;
import java.util.List;

public interface PagedServiceObserver<T> extends ServiceObserver{
    void handleSuccess(List<T> items, boolean hasMore);
}
