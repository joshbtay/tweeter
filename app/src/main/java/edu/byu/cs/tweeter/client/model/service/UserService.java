package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;



public class UserService {


    public interface GetUserObserver extends ServiceObserver {
        void handleSuccess(User user);
    }
    public void getUser(AuthToken authToken, String alias, GetUserObserver observer){
        GetUserTask getUserTask = new GetUserTask(authToken, alias, new GetUserHandler(observer));
        getUserTask.execute();
    }



}


