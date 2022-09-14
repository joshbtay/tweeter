package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

public interface PagedView<T> extends NavigateView{
    void setLoading(boolean value);
    void addItems(List<T> items);
}
