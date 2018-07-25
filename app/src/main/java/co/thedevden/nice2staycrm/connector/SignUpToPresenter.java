package co.thedevden.nice2staycrm.connector;

public interface SignUpToPresenter {

    void performSignUp(String firstname,String surname,String bussiness,String email,String pass,String confirmPass);
    void signUpResponse(boolean check);
    void signUpError(String message);
}
