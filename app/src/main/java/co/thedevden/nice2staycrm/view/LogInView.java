package co.thedevden.nice2staycrm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.thedevden.nice2staycrm.MainActivity;
import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.connector.LoginPresenterToView;
import co.thedevden.nice2staycrm.connector.LoginToPresenter;
import co.thedevden.nice2staycrm.presenter.LogInPresenter;

public class LogInView extends AppCompatActivity implements LoginPresenterToView {

    EditText userName,password;

    LoginToPresenter logInPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        userName = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        logInPresenter = new LogInPresenter(this,this);

    }

    public void signInOnClick(View view) {
        String user,pass;


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
    public void userNameError() { userName.setError("Please Enter Username");}

    @Override
    public void passwordError() { password.setError("Please Enter Password"); }

    @Override
    public void loginSuccess(boolean message) {
        Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LogInView.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void signUpContinue(View view) {
       Intent intent = new Intent(LogInView.this,SignUpView.class);
       startActivity(intent);
       finish();
    }
}
