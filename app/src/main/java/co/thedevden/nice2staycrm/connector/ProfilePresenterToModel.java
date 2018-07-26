package co.thedevden.nice2staycrm.connector;

public interface ProfilePresenterToModel {

    void editProfile(String firstname,String surname,String email,String bussiness,String pass,String confirmPass);
    void viewProfile();

}
