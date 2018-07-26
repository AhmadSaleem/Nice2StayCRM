package co.thedevden.nice2staycrm.presenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

import co.thedevden.nice2staycrm.connector.ProfilePresenterToModel;
import co.thedevden.nice2staycrm.connector.ProfilePresenterToView;
import co.thedevden.nice2staycrm.connector.ProfileToPresenter;
import co.thedevden.nice2staycrm.model.ProfileModel;

public class ProfilePresenter implements ProfileToPresenter {

    Context context;
    ProfilePresenterToView view;
    ProfilePresenterToModel model;

    public ProfilePresenter(Context context, ProfilePresenterToView view) {
        this.context = context;
        model = new ProfileModel(context,this);
        this.view = view;
    }


    @Override
    public void editUserProfile(String firstname, String surname, String email, String bussiness, String pass, String confirmPass) {


        if (TextUtils.isEmpty(firstname) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(bussiness) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirmPass)) {
            if (TextUtils.isEmpty(firstname)) {
                view.showErrorFirstName();
            }
            if (TextUtils.isEmpty(surname)) {
                view.showErrorSurName();
            }
            if (TextUtils.isEmpty(bussiness)) {
                view.showErrorBussiness();
            }
            if (TextUtils.isEmpty(email)) {
                view.showErrorEmail();
            }
            if (TextUtils.isEmpty(pass)) {
                view.showErrorPass();
            }
            if (TextUtils.isEmpty(confirmPass)) {
                view.showErrorCnfrmPass();
            }
        } else
        {
            if (!(pass.equals(confirmPass)))
            {
                view.passMismatch();

            } else
                {

                    model.editProfile(firstname, surname, email, bussiness, pass, confirmPass);
            }

        }
    }
    @Override
    public void viewUserProfile() {

    }

    @Override
    public void editSuccess() {
        view.editSuccess();
    }

    @Override
    public void editError(String message) {
        view.showError(message);
    }

    @Override
    public void viewProfile() {
        model.viewProfile();
    }

    @Override
    public void viewSuccess(ArrayList<String> user) {
        view.viewSuccess(user);
    }
}
