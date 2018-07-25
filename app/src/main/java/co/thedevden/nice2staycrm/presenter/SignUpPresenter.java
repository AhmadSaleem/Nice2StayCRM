package co.thedevden.nice2staycrm.presenter;

import android.content.Context;
import android.text.TextUtils;

import co.thedevden.nice2staycrm.connector.SignUpPresenterToModel;
import co.thedevden.nice2staycrm.connector.SignUpPresenterToView;
import co.thedevden.nice2staycrm.connector.SignUpToPresenter;
import co.thedevden.nice2staycrm.model.SignUpModel;

public class SignUpPresenter implements SignUpToPresenter {

    Context context;
    SignUpPresenterToView signUpview;
    SignUpPresenterToModel tomodel;

    public SignUpPresenter(Context context, SignUpPresenterToView view) {
        this.context = context;
        signUpview = view;
        tomodel = new SignUpModel(context,this);
    }


    @Override
    public void performSignUp(String firstname, String surname, String bussiness, String email, String pass, String confirmPass) {

        if(TextUtils.isEmpty(firstname)||TextUtils.isEmpty(surname)||TextUtils.isEmpty(bussiness)||TextUtils.isEmpty(email)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(confirmPass))
        {
            if(TextUtils.isEmpty(firstname))
            {
                signUpview.showErrorFirstName();
            }
            if(TextUtils.isEmpty(surname))
            {
                signUpview.showErrorSurName();
            }
            if(TextUtils.isEmpty(bussiness))
            {
                signUpview.showErrorBussiness();
            }
            if(TextUtils.isEmpty(email))
            {
                signUpview.showErrorEmail();
            }
            if(TextUtils.isEmpty(pass))
            {
                signUpview.showErrorPass();
            }
            if (TextUtils.isEmpty(confirmPass))
            {
                signUpview.showErrorCnfrmPass();
            }
        }
        else
        {
           if(!(pass.equals(confirmPass)))
           {
               signUpview.passMismatch();
           }
           else
           {
               tomodel.signUp(firstname,surname, bussiness, email, pass, confirmPass);
           }


        }




    }

    @Override
    public void signUpResponse(boolean check) {

        signUpview.signUpSuccess();
    }

    @Override
    public void signUpError(String message) {
        signUpview.signUpError(message);
    }
}
