package co.thedevden.nice2staycrm.connector;

public interface LoginToPresenter {

    void performLogin(String userName, String password);
    void onLoginResponse(boolean success);
    void onError(String message);

}
