package co.thedevden.nice2staycrm.connector;

public interface LoginPresenterToView {

    void userNameError();
    void passwordError();
    void loginSuccess(boolean message);
    void loginError(String message);

}
