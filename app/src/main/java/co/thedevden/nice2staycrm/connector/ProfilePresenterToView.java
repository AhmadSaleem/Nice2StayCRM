package co.thedevden.nice2staycrm.connector;

import java.util.ArrayList;

public interface ProfilePresenterToView {

    void editProfile();
    void viewProfile();
    void editSuccess();
    void viewSuccess(ArrayList<String> user);
    void showError(String message);
    void showErrorFirstName();
    void showErrorSurName();
    void showErrorBussiness();
    void showErrorEmail();
    void showErrorPass();
    void showErrorCnfrmPass();
    void passMismatch();
}
