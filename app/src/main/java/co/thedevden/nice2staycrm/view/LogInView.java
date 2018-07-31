package co.thedevden.nice2staycrm.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import co.thedevden.nice2staycrm.utils.ConnectivityReceiver;

public class LogInView extends AppCompatActivity implements LoginPresenterToView {

    EditText userName,password;
    ProgressBar progressBar;
    boolean loggedIn;
    AlertDialog.Builder builder;
    private BroadcastReceiver broadcastReceiver;
    LoginToPresenter logInPresenter;
    View view,view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = getLayoutInflater().inflate(R.layout.activity_log_in,null);
        view2 = getLayoutInflater().inflate(R.layout.no_internet_found,null);

        setContentView(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        builder = new AlertDialog.Builder(this);


//        userName = (EditText) findViewById(R.id.editText);
//        password = (EditText) findViewById(R.id.editText2);
        progressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
        logInPresenter = new LogInPresenter(this,this);


    }

    public void checkInternet()
    {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(ConnectivityReceiver.isConnected(getApplicationContext()))
                {
                    setContentView(view);
                    return;
                }
                else
                {
                    setContentView(view2);
                    alertConection();
                }
            }
        };

        registerReceiver(broadcastReceiver,intentFilter);

    }


    @Override
    protected void onStop() {

        unregisterReceiver(broadcastReceiver);
        super.onStop();
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
    userName.setError("Please Enter Valid Mail");}

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

        checkInternet();


        progressBar.setVisibility(View.VISIBLE);

        loggedIn  = SharedPreferencesUtils.getInstance(this).getBoolanValue("istoken",false);

        Intent service = new Intent(LogInView.this,RefreshToken.class);

        if(loggedIn){

            Toast.makeText(this, "You are already logged In", Toast.LENGTH_SHORT).show();

            startService(service);

            progressBar.setVisibility(View.GONE);

           Intent intent = new Intent(LogInView.this,MainActivity.class);
           startActivity(intent);
           finish();

        }
        else
        {
            progressBar.setVisibility(View.GONE);
            stopService(service);
        }


    }
    private void alertConection()
    {

        builder.setTitle("No Internet Connection!");
        builder.setMessage("Sorry! no Internet connectivety detected. Please Reconnect and try again.");
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                boolean isConnected = ConnectivityReceiver.isConnected(getApplicationContext());
                if(!isConnected)
                    alertConection();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
