package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends AuthenticatedPresenter<PagedView>{
    public PagedPresenter(PagedView view, User targetUser) {
        super(view);
        this.targetUser = targetUser;
        getUserObserver = new UserService.GetUserObserver() {
            @Override
            public void handleSuccess(User user) {
                view.navigateToUser(user);
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };
    }
    protected User targetUser;
    protected T lastItem;
    protected boolean hasMorePages = true;
    protected boolean isLoading = false;
    protected static final int PAGE_SIZE = 10;
    protected UserService.GetUserObserver getUserObserver;
    public void loadMoreItems(){
        if(!isLoading &&hasMorePages){
            isLoading = true;
            view.setLoading(true);
            sendRequest();
        }
    }

    public void addItems(List<T> items, boolean hasMorePages){
        if (items.size() > 0){
            lastItem = items.get(items.size() - 1);
        }
        else{
            lastItem = null;
        }
        view.setLoading(false);
        view.addItems(items);
        this.hasMorePages = hasMorePages;
        isLoading = false;
    }
    protected abstract void sendRequest();
    public void goToUser(String alias){
        view.displayInfoMessage("Getting user's profile...");
        new UserService().getUser(authToken, alias, getUserObserver);
    }

}
