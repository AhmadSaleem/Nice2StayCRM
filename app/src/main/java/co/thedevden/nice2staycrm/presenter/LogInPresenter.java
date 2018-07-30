package co.thedevden.nice2staycrm.presenter;

import android.content.Context;
import android.text.TextUtils;

import co.thedevden.nice2staycrm.connector.LoginPresenterToModel;
import co.thedevden.nice2staycrm.connector.LoginPresenterToView;
import co.thedevden.nice2staycrm.connector.LoginToPresenter;
import co.thedevden.nice2staycrm.model.LoginModel;
import co.thedevden.nice2staycrm.view.LogInView;


public class LogInPresenter implements LoginToPresenter {

    LoginPresenterToView logInView;
    Context context;
    LoginPresenterToModel model;



    public LogInPresenter(Context context,LoginPresenterToView view) {

        this.context = context;
        model = new LoginModel(context,this);
        logInView = view;

    }

    @Override
    public void performLogin(String userName, String password) {

        if (TextUtils.isEmpty(userName)|| TextUtils.isEmpty(password))
        {
            if (TextUtils.isEmpty(password)) {
                logInView.passwordError();
            }
            if (TextUtils.isEmpty(userName)) {
                logInView.userNameError();
            }
        }
        else {

            if(isEmailValid(userName))
                model.PerformLoginOperation(userName,password);
            else
                logInView.userNameError();


        }
    }


//    @Override
//    public void isLoggedIn() {
//        model.isLoggedIn();
//    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onLoginResponse(boolean message) {
        logInView.loginSuccess(message);
    }

    @Override
    public void onError(String message) {
        logInView.loginError(message);
    }


}
