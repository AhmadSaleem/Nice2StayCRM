package co.thedevden.nice2staycrm.connector;

import java.util.ArrayList;

public interface ProfileToPresenter {

    void editUserProfile(String firstname,String surname,String email,String bussiness,String pass,String confirmPass);
    void viewUserProfile();
    void editSuccess();
    void  editError(String message);
    void viewProfile();
    void viewSuccess(ArrayList<String> user);


}
