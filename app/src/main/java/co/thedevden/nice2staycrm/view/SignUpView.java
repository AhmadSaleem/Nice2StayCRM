package co.thedevden.nice2staycrm.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import co.thedevden.nice2staycrm.MainActivity;
import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.connector.SignUpPresenterToView;
import co.thedevden.nice2staycrm.connector.SignUpToPresenter;
import co.thedevden.nice2staycrm.presenter.SignUpPresenter;
import co.thedevden.nice2staycrm.service.RefreshToken;

public class SignUpView extends AppCompatActivity implements SignUpPresenterToView {

    EditText firstname_Edt_Txt,lastname_Edt_Txt,bussName_Edt_Txt,
            email_Edt_Txt,pass_Edt_Txt,cnfrmpass_Edt_Txt;
    Button signUp;
    ProgressBar progressBar;

    SignUpToPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname_Edt_Txt = (EditText) findViewById(R.id.editText3);
        lastname_Edt_Txt = (EditText) findViewById(R.id.editText4);
        bussName_Edt_Txt = (EditText) findViewById(R.id.editText5);
        email_Edt_Txt = (EditText) findViewById(R.id.editText6);
        pass_Edt_Txt = (EditText) findViewById(R.id.editText7);
        cnfrmpass_Edt_Txt = (EditText) findViewById(R.id.editText8);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSignUp);

        presenter = new SignUpPresenter(this,this);

    }

    public void signUp(View view) {

        String firstname,lastname,bussName,email,pass,cnfrmPass;

        firstname = firstname_Edt_Txt.getText().toString();
        lastname = lastname_Edt_Txt.getText().toString();
        bussName = bussName_Edt_Txt.getText().toString();
        email = email_Edt_Txt.getText().toString();
        pass = pass_Edt_Txt.getText().toString();
        cnfrmPass = cnfrmpass_Edt_Txt.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        presenter.performSignUp(firstname,lastname,bussName,email,pass,cnfrmPass);


    }

    @Override
    public void showErrorFirstName() {
        progressBar.setVisibility(View.GONE);
        firstname_Edt_Txt.setError("Enter First Name");
    }

    @Override
    public void showErrorSurName() {
        progressBar.setVisibility(View.GONE);
        lastname_Edt_Txt.setError("Enter Sur Name");
    }

    @Override
    public void showErrorBussiness() {
        progressBar.setVisibility(View.GONE);
        bussName_Edt_Txt.setError("Enter Bussiness Name");
    }

    @Override
    public void showErrorEmail() {
        progressBar.setVisibility(View.GONE);
        email_Edt_Txt.setError("Enter Email");
    }

    @Override
    public void showErrorPass() {
        progressBar.setVisibility(View.GONE);
        pass_Edt_Txt.setError("Enter pass");

    }

    @Override
    public void showErrorCnfrmPass() {

        progressBar.setVisibility(View.GONE);
        cnfrmpass_Edt_Txt.setError("Enter Confirm Passeord");
    }

    @Override
    public void passMismatch() {
        progressBar.setVisibility(View.GONE);
        pass_Edt_Txt.setError("Mismatch");
        cnfrmpass_Edt_Txt.setError("Mismatch");
    }

    @Override
    public void signUpSuccess() {

        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "SignUp Sucessfull", Toast.LENGTH_SHORT).show();

        Intent service = new Intent(SignUpView.this,RefreshToken.class);
        startService(service);

        Intent intent = new Intent(SignUpView.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void signUpError(String message)
    {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void logInContinue(View view) {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(SignUpView.this, LogInView.class);
        startActivity(intent);
        finish();

    }
}
