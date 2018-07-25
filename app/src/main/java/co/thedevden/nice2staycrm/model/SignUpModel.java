package co.thedevden.nice2staycrm.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.thedevden.nice2staycrm.MainActivity;
import co.thedevden.nice2staycrm.connector.SignUpPresenterToModel;
import co.thedevden.nice2staycrm.connector.SignUpToPresenter;
import co.thedevden.nice2staycrm.utils.Config;

public class SignUpModel implements SignUpPresenterToModel
{

    Context context;
    SignUpToPresenter presenter;
    RequestQueue requestQueue;
    Long tokenExpiry;
    String token;
    boolean istoken;

    public SignUpModel(Context context, SignUpToPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void signUp(String firstname, String surname, String bussiness, final String email, String pass, String confirmPass) {

        HashMap<String, JSONObject> params = new HashMap<String, JSONObject>();
        JSONObject json = new JSONObject();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",firstname);
            jsonObject.put("surname",surname);
            jsonObject.put("email",email);
            jsonObject.put("password",pass);
            jsonObject.put("password_confirmation",confirmPass);
            jsonObject.put("business_name",bussiness);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json.put("business_owner",jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("business_owner", jsonObject);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Config.SIGNUP_URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText( getApplicationContext(), "responseSignup", Toast.LENGTH_SHORT).show();
                        Log.d("ResponseSignUP", response.toString());

                        try {


                            String name,surname;
                            name = response.get("name").toString();
                            surname = response .get("surname").toString();

                            SharedPreferencesUtils.getInstance(context).setValue("name",name);
                            SharedPreferencesUtils.getInstance(context).setValue("surname",surname);
                            SharedPreferencesUtils.getInstance(context).setValue("personEmail",email);



                            tokenExpiry = Long.parseLong(response.get("token_expires_at").toString());
//                                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//                                    tokenExpiry = timestamp.getTime();

                            Log.d("tokenExpiryLong",tokenExpiry.toString());
                            Log.d("tokenExpiryResponse",response.get("token_expires_at").toString());

                            token = response.get("auth_token").toString();
                            Log.d("tokenSIGNUP",token);
                            istoken=true;




                            if(istoken)
                            {

                                SharedPreferencesUtils.getInstance(context).setValue("token",token);
                                SharedPreferencesUtils.getInstance(context).setValue("istoken",istoken);
                                SharedPreferencesUtils.getInstance(context).setValue("tokenExpiry",tokenExpiry);

                                Log.d("tokensentSIGNUP",String.valueOf(istoken));
                            }


//                            editor.apply();
//
//                            Intent service = new Intent(SignUp.this,Refresh.class);
//                            startService(service);
//
//                            progressBar.setVisibility(View.GONE);

//                            Intent intent = new Intent(SignUp.this,MainActivity.class);
//                            intent.putExtra("name",name);
//                            intent.putExtra("surname",surname);
//                            intent.putExtra("personEmail",emailStr);
//                            startActivity(intent);
//                            finish();//new line

                            presenter.signUpResponse(true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                presenter.signUpError(error.toString());

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };

        //Queue.getInstance(SignUp.this).addtoRequestQueue(req);
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(req);

    }
}
