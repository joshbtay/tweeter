package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends AuthenticatedPresenter<MainView>{
    private User currentUser;
    private FollowService.FollowObserver followObserver;
    private FollowService.CountObserver FollowersCountObserver;
    private FollowService.UnfollowObserver unfollowObserver;
    private FollowService.CountObserver FollowingCountObserver;
    private LoginService.LogoutObserver logoutObserver;
    private FollowService.IsFollowerObserver isFollowerObserver;
    private StatusService.PostStatusObserver postStatusObserver;
    private StatusService statusService;

    public MainPresenter(MainView view, User currentUser){
        super(view);
        this.currentUser = currentUser;
        followObserver = new FollowService.FollowObserver() {
            @Override
            public void handleSuccess() {
                updateCounts();
                view.updateFollowButton(false);
                view.enableFollowButton();
            }
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };
        unfollowObserver = new FollowService.UnfollowObserver() {
            @Override
            public void handleSuccess() {
                updateCounts();
                view.enableFollowButton();
                view.updateFollowButton(true);
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };
        FollowersCountObserver = new FollowService.CountObserver() {

            @Override
            public void handleSuccess(int count) {
                view.updateFollowersCount(count);
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };

        FollowingCountObserver = new FollowService.CountObserver() {
            @Override
            public void handleSuccess(int count) {
                view.updateFollowingCount(count);
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };

        logoutObserver = new LoginService.LogoutObserver() {
            @Override
            public void handleSuccess() {
                Cache.getInstance().clearCache();
                view.logoutUser();
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };

        isFollowerObserver = new FollowService.IsFollowerObserver() {
            @Override
            public void handleSuccess(boolean isFollower) {
                view.setIsFollower(isFollower);
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };
        postStatusObserver = new StatusService.PostStatusObserver() {
            @Override
            public void handleSuccess() {
                view.displayInfoMessage("Status posted");
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        };

    }

    public StatusService getStatusService(){
        if (statusService == null){
            return new StatusService();
        }
        else{
            return statusService;
        }
    }

    public void logout(){
        view.displayInfoMessage("Logging out");
        new LoginService().logout(authToken, logoutObserver);
    }

    public void updateCounts(){
        new FollowService().getCounts(authToken, currentUser, FollowersCountObserver, FollowingCountObserver);
    }

    public void isFollower() {
        new FollowService().isFollower(authToken, Cache.getInstance().getCurrUser(), currentUser, isFollowerObserver);
    }

    public void follow(){
        new FollowService().follow(authToken, currentUser, followObserver);
        view.displayInfoMessage("Adding " + currentUser.getAlias() +"...");
    }

    public void unfollow(){
        new FollowService().unfollow(authToken, currentUser, unfollowObserver);
        view.displayInfoMessage("Removing " + currentUser.getAlias() +"...");
    }

    public void postStatus(String post){
        if(post == null || post.length() == 0){
            view.displayErrorMessage("Can't post empty status");
            return;
        }
        view.displayInfoMessage("Posting status...");
            Status status = new Status(post, Cache.getInstance().getCurrUser(),
                    getFormattedDateTime(), parseURLs(post), parseMentions(post));
            getStatusService().postStatus(authToken, status, postStatusObserver);
    }

    private String getFormattedDateTime() {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        String formatted = null;
        try {
            formatted = statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
        } catch (ParseException e) {
            formatted = "Invalid Date";
        }
        return formatted;
    }

    private List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    private List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    private int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

}
