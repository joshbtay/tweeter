package edu.byu.cs.tweeter.client.presenter;

public interface MainView extends View{
    void logoutUser();
    void updateFollowersCount(int count);
    void updateFollowingCount(int count);
    void setIsFollower(boolean follower);
    void updateFollowButton(boolean removed);
    void enableFollowButton();
}
