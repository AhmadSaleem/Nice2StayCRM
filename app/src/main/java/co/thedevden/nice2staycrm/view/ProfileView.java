package co.thedevden.nice2staycrm.view;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.connector.ProfilePresenterToView;
import co.thedevden.nice2staycrm.connector.ProfileToPresenter;
import co.thedevden.nice2staycrm.presenter.ProfilePresenter;

public class ProfileView extends AppCompatActivity implements ProfilePresenterToView{

    EditText fNamePro_Edt_Txt,lNamePro_Edt_Txt,emailPro_Edt_Txt,bussPro_Edt_Txt;
    EditText profilePass_Edt_Txt,cnfrmProPass_Edt_Txt;
    TextView cnfrmTv,passtv;
    TextInputLayout TILcnfrmPass,TILPass;
    Button submit_profile;
    ImageView editImageview;
    String firstNameStr,lastNameStr,emailStr,passwordStr,confirmPasswordStr,bussinessNameStr;
    ProfileToPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        fNamePro_Edt_Txt = (EditText) findViewById(R.id.firstname_profile);
        lNamePro_Edt_Txt = (EditText) findViewById(R.id.lastname_profile);
        emailPro_Edt_Txt = (EditText) findViewById(R.id.email_profile);
        bussPro_Edt_Txt = (EditText) findViewById(R.id.businessName_profile);
        submit_profile = (Button) findViewById(R.id.submitProfileButton);
        editImageview = (ImageView) findViewById(R.id.edit);
        profilePass_Edt_Txt = (EditText) findViewById(R.id.profilePassword);
        cnfrmProPass_Edt_Txt = (EditText) findViewById(R.id.cnfmprofilePassword);
        TILcnfrmPass = (TextInputLayout) findViewById(R.id.cnfmpasswordProTIL);
        TILPass = (TextInputLayout) findViewById(R.id.passwordProTIL);
        cnfrmTv = (TextView) findViewById(R.id.cnfmpasswordshowtv);
        passtv = (TextView) findViewById(R.id.passwordshowtv);

        presenter = new ProfilePresenter(this,this);

        editImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fNamePro_Edt_Txt.setEnabled(true);
                lNamePro_Edt_Txt.setEnabled(true);
                bussPro_Edt_Txt.setEnabled(true);

                cnfrmProPass_Edt_Txt.setVisibility(View.VISIBLE);
                cnfrmTv.setVisibility(View.VISIBLE);
                passtv.setVisibility(View.VISIBLE);
                TILcnfrmPass.setVisibility(View.VISIBLE);
                TILPass.setVisibility(View.VISIBLE);
                submit_profile.setVisibility(View.VISIBLE);

            }
        });

        submit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editProfile();
            }
        });

        viewProfile();

    }


    @Override
    public void editProfile() {

        firstNameStr = fNamePro_Edt_Txt.getText().toString();
        lastNameStr = lNamePro_Edt_Txt.getText().toString();
        emailStr = emailPro_Edt_Txt.getText().toString();
        passwordStr = profilePass_Edt_Txt.getText().toString();
        confirmPasswordStr = cnfrmProPass_Edt_Txt.getText().toString();
        bussinessNameStr = bussPro_Edt_Txt.getText().toString();

        if((TextUtils.isEmpty(passwordStr)||TextUtils.isEmpty(confirmPasswordStr)))
        {
            profilePass_Edt_Txt.setError("Plz Enter Password");
            cnfrmProPass_Edt_Txt.setError("Plz Confirm");

        }
        else
        {

            presenter.editUserProfile(firstNameStr,lastNameStr,emailStr,bussinessNameStr,passwordStr,confirmPasswordStr);

        }



    }

    @Override
    public void viewProfile() {

        presenter.viewProfile();

    }

    @Override
    public void editSuccess() {

        fNamePro_Edt_Txt.setEnabled(false);
        lNamePro_Edt_Txt.setEnabled(false);
        bussPro_Edt_Txt.setEnabled(false);

        cnfrmProPass_Edt_Txt.setVisibility(View.GONE);
        cnfrmTv.setVisibility(View.GONE);
        passtv.setVisibility(View.GONE);
        TILcnfrmPass.setVisibility(View.GONE);
        TILPass.setVisibility(View.GONE);
        submit_profile.setVisibility(View.GONE);

        Toast.makeText(this, "Profile Edited Sucessfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void viewSuccess(ArrayList<String> user) {
        fNamePro_Edt_Txt.setText(user.get(0));
        lNamePro_Edt_Txt.setText(user.get(1));
        emailPro_Edt_Txt.setText(user.get(2));
        bussPro_Edt_Txt.setText(user.get(3));

    }


    @Override
    public void showError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorFirstName() {
        fNamePro_Edt_Txt.setError("Enter First Name");
    }

    @Override
    public void showErrorSurName() {

        lNamePro_Edt_Txt.setError("Enter Sur Name");
    }

    @Override
    public void showErrorBussiness() {

        bussPro_Edt_Txt.setError("Enter Bussiness Name");
    }

    @Override
    public void showErrorEmail() {

        emailPro_Edt_Txt.setError("Enter Email");
    }

    @Override
    public void showErrorPass() {
        profilePass_Edt_Txt.setError("Enter pass");

    }

    @Override
    public void showErrorCnfrmPass() {

        cnfrmProPass_Edt_Txt.setError("Enter Confirm Passeord");
    }

    @Override
    public void passMismatch() {
        profilePass_Edt_Txt.setError("Mismatch");
        cnfrmProPass_Edt_Txt.setError("Mismatch");
    }
}
