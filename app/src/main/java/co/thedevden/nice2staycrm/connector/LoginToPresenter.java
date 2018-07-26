package co.thedevden.nice2staycrm.connector;

import java.util.ArrayList;

public interface LoginToPresenter {

    void performLogin(String userName, String password);
    void onLoginResponse(boolean success);
    void onError(String message);


}
