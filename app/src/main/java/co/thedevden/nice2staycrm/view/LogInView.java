package co.thedevden.nice2staycrm.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import co.thedevden.nice2staycrm.MainActivity;
import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.connector.LoginPresenterToView;
import co.thedevden.nice2staycrm.connector.LoginToPresenter;
import co.thedevden.nice2staycrm.model.SharedPreferencesUtils;
import co.thedevden.nice2staycrm.presenter.LogInPresenter;
import co.thedevden.nice2staycrm.service.RefreshToken;

public class LogInView extends AppCompatActivity implements LoginPresenterToView {

    EditText userName,password;
    ProgressBar progressBar;
    boolean loggedIn;

    LoginToPresenter logInPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        userName = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        progressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
        logInPresenter = new LogInPresenter(this,this);

    }

    public void signInOnClick(View view) {
        String user,pass;

        progressBar.setVisibility(View.VISIBLE);

        user = userName.getText().toString();
        pass = password.getText().toString();

        logInPresenter.performLogin(user,pass);

    }

    public void noAccountClick(View view) {
        Intent intent = new Intent(LogInView.this,SignUpView.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void userNameError() { progressBar.setVisibility(View.GONE);
    userName.setError("Please Enter Username");}

    @Override
    public void passwordError() { progressBar.setVisibility(View.GONE);
    password.setError("Please Enter Password"); }

    @Override
    public void loginSuccess(boolean message) {
        progressBar.setVisibility(View.GONE);

        Intent service = new Intent(LogInView.this,RefreshToken.class);
        startService(service);

        Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LogInView.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginError(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();


    }

    public void signUpContinue(View view) {
       Intent intent = new Intent(LogInView.this,SignUpView.class);
       startActivity(intent);
       finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        progressBar.setVisibility(View.VISIBLE);

        loggedIn  = SharedPreferencesUtils.getInstance(this).getBoolanValue("istoken",false);

        Intent service = new Intent(LogInView.this,RefreshToken.class);

        if(loggedIn){

            Toast.makeText(this, "You are already logged In", Toast.LENGTH_SHORT).show();

            startService(service);

            progressBar.setVisibility(View.GONE);

           Intent intent = new Intent(LogInView.this,MainActivity.class);
           startActivity(intent);

        }
        else
        {
            progressBar.setVisibility(View.GONE);
            stopService(service);
        }


    }
}
