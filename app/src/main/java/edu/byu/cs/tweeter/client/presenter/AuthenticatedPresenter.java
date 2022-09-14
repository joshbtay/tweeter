package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public class AuthenticatedPresenter<T extends View> extends Presenter<T>{
    public AuthenticatedPresenter(T view) {
        super(view);
        authToken = Cache.getInstance().getCurrUserAuthToken();
    }
    protected AuthToken authToken;
}
