package co.thedevden.nice2staycrm.connector;

public interface SignUpPresenterToView {

    void showErrorFirstName();
    void showErrorSurName();
    void showErrorBussiness();
    void showErrorEmail();
    void showErrorPass();
    void showErrorCnfrmPass();
    void passMismatch();
    void signUpSuccess();
    void signUpError(String message);

}
