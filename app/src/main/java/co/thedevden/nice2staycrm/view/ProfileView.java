package co.thedevden.nice2staycrm.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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
import co.thedevden.nice2staycrm.utils.ConnectivityReceiver;

public class ProfileView extends AppCompatActivity implements ProfilePresenterToView{

    EditText fNamePro_Edt_Txt,lNamePro_Edt_Txt,emailPro_Edt_Txt,bussPro_Edt_Txt;
    EditText profilePass_Edt_Txt,cnfrmProPass_Edt_Txt;
    TextView cnfrmTv,passtv;
    TextInputLayout TILcnfrmPass,TILPass;
    Button submit_profile;
    ImageView editImageview;
    String firstNameStr,lastNameStr,emailStr,passwordStr,confirmPasswordStr,bussinessNameStr;
    ProfileToPresenter presenter;
    AlertDialog.Builder builder;
    BroadcastReceiver broadcastReceiver;
    View belowpass;
    View view,view2;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        view = getLayoutInflater().inflate(R.layout.activity_profile_view,null);
        view2 = getLayoutInflater().inflate(R.layout.no_internet_found,null);
        setContentView(view);

        fNamePro_Edt_Txt = (EditText) findViewById(R.id.firstname_profile);
        lNamePro_Edt_Txt = (EditText) findViewById(R.id.lastname_profile);
        emailPro_Edt_Txt = (EditText) findViewById(R.id.email_profile);
        bussPro_Edt_Txt = (EditText) findViewById(R.id.businessName_profile);
        submit_profile = (Button) findViewById(R.id.submitProfileButton);
        editImageview = (ImageView) findViewById(R.id.edit);
        profilePass_Edt_Txt = (EditText) findViewById(R.id.profilePassword);
        cnfrmProPass_Edt_Txt = (EditText) findViewById(R.id.cnfmprofilePassword);
        cnfrmTv = (TextView) findViewById(R.id.cnfmpasswordshowtv);
        passtv = (TextView) findViewById(R.id.passwordshowtv);
        belowpass = (View) findViewById(R.id.belowpassview);
        progressBar = (ProgressBar) findViewById(R.id.profileProgressBar);

        builder = new AlertDialog.Builder(this);

        presenter = new ProfilePresenter(this,this);

        editImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fNamePro_Edt_Txt.setEnabled(true);
                lNamePro_Edt_Txt.setEnabled(true);
                bussPro_Edt_Txt.setEnabled(true);
                fNamePro_Edt_Txt.setSelection(fNamePro_Edt_Txt.getText().length());
                fNamePro_Edt_Txt.requestFocus();


                belowpass.setVisibility(View.VISIBLE);
                cnfrmProPass_Edt_Txt.setVisibility(View.VISIBLE);
                profilePass_Edt_Txt.setVisibility(View.VISIBLE);
                cnfrmTv.setVisibility(View.VISIBLE);
                passtv.setVisibility(View.VISIBLE);
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

    @Override
    protected void onResume() {
        checkInternet();
        super.onResume();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    @Override
    public void editProfile() {


        firstNameStr = fNamePro_Edt_Txt.getText().toString();
        lastNameStr = lNamePro_Edt_Txt.getText().toString();
        emailStr = emailPro_Edt_Txt.getText().toString();
        passwordStr = profilePass_Edt_Txt.getText().toString();
        confirmPasswordStr = cnfrmProPass_Edt_Txt.getText().toString();
        bussinessNameStr = bussPro_Edt_Txt.getText().toString();



            presenter.editUserProfile(firstNameStr,lastNameStr,emailStr,bussinessNameStr,passwordStr,confirmPasswordStr);

        



    }

    @Override
    public void viewProfile() {

        presenter.viewProfile();

    }

    @Override
    public void editSuccess() {

        progressBar.setVisibility(View.GONE);
        fNamePro_Edt_Txt.setEnabled(false);
        lNamePro_Edt_Txt.setEnabled(false);
        bussPro_Edt_Txt.setEnabled(false);

        cnfrmProPass_Edt_Txt.setVisibility(View.GONE);
        profilePass_Edt_Txt.setVisibility(View.GONE);
        cnfrmTv.setVisibility(View.GONE);
        passtv.setVisibility(View.GONE);
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

        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorFirstName() {
        progressBar.setVisibility(View.GONE);

        fNamePro_Edt_Txt.setError("Enter First Name");

    }

    @Override
    public void showErrorSurName() {

        progressBar.setVisibility(View.GONE);
        lNamePro_Edt_Txt.setError("Enter Sur Name");

    }

    @Override
    public void showErrorBussiness() {
        progressBar.setVisibility(View.GONE);
        bussPro_Edt_Txt.setError("Enter Bussiness Name");

    }

    @Override
    public void showErrorEmail() {

        progressBar.setVisibility(View.GONE);
        emailPro_Edt_Txt.setError("Enter Email");

    }

    @Override
    public void showErrorPass() {
        progressBar.setVisibility(View.GONE);
        profilePass_Edt_Txt.setError("Enter pass");


    }

    @Override
    public void showErrorCnfrmPass() {

        progressBar.setVisibility(View.GONE);
        cnfrmProPass_Edt_Txt.setError("Enter Confirm Password");


    }

    @Override
    public void passMismatch() {
        progressBar.setVisibility(View.GONE);
        profilePass_Edt_Txt.setError("Mismatch");
        cnfrmProPass_Edt_Txt.setError("Mismatch");

    }
}
