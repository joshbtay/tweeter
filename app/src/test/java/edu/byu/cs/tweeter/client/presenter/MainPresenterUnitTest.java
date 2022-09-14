package edu.byu.cs.tweeter.client.presenter;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenterUnitTest {
    private MainView mockView;
    private StatusService mockStatusService;
    private Cache mockCache;
    private User mockUser;
    private MainPresenter mainPresenterSpy;

    @BeforeEach
    public void setup(){

        mockView = Mockito.mock(MainView.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockCache = Mockito.mock(Cache.class);
        mockUser = new User("firstname", "lastname", "@alias", "image");
        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView, mockUser));
        Mockito.doReturn(mockStatusService).when(mainPresenterSpy).getStatusService();

        Cache.setInstance(mockCache);
    }

    @Test
    public void testPostStatus_Succeeds() {
        Answer<Void> successAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgumentAt(2, StatusService.PostStatusObserver.class);
                Status status = invocation.getArgumentAt(1, Status.class);
                observer.handleSuccess();
                observer.handleFailure(status.getPost());
                return null;
            }
        };
        Mockito.doAnswer(successAnswer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("sup");
        Mockito.verify(mockView).displayInfoMessage("Posting status...");
        Mockito.verify(mockView).displayInfoMessage("Status posted");
        Mockito.verify(mockView).displayErrorMessage("sup");
    }

    @Test
    public void testPostStatus_Fails() {
        Answer<Void> successAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgumentAt(2, StatusService.PostStatusObserver.class);
                observer.handleFailure("Failed to post status: TEST ERROR");
                return null;
            }
        };
        Mockito.doAnswer(successAnswer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("sup");
        Mockito.verify(mockView).displayInfoMessage("Posting status...");
        Mockito.verify(mockView).displayErrorMessage("Failed to post status: TEST ERROR");
    }

    @Test
    public void testPostStatus_FailsEmpty() {
        mainPresenterSpy.postStatus("");
        mainPresenterSpy.postStatus(null);
        Mockito.verify(mockView, Mockito.times(2)).displayErrorMessage("Can't post empty status");
    }



}
