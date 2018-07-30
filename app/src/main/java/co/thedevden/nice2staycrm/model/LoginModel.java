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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import co.thedevden.nice2staycrm.MainActivity;
import co.thedevden.nice2staycrm.connector.LoginPresenterToModel;
import co.thedevden.nice2staycrm.connector.LoginToPresenter;
import co.thedevden.nice2staycrm.presenter.LogInPresenter;
import co.thedevden.nice2staycrm.utils.Config;

public class LoginModel implements LoginPresenterToModel {

    Context context;
    LoginToPresenter presenter;
    Long tokenExpiry;
    String token;
    boolean istoken;
    RequestQueue requestQueue;


    public LoginModel(Context context,LoginToPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

//    public void login(String userName,String password)
//    {
//        try {
//            performLoginOperation(userName,password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }



    @Override
    public void PerformLoginOperation(final String userName, String password) {

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",userName);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Config.LOGIN_URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText( getApplicationContext(), "responseSignup", Toast.LENGTH_SHORT).show();
                        Log.d("ResponseLogin", response.toString());

                        try {

                            String name,surname;
                            name = response.get("name").toString();
                            surname = response .get("surname").toString();

                            SharedPreferencesUtils.getInstance(context).setValue("name",name);
                            SharedPreferencesUtils.getInstance(context).setValue("surname",surname);
                            SharedPreferencesUtils.getInstance(context).setValue("personEmail",userName);

                            tokenExpiry = Long.parseLong(response.get("token_expires_at").toString());

                            token = response.get("auth_token").toString();
                            Log.d("tokenLogIn",token);
                            istoken=true;



                            if(istoken)
                            {

                                SharedPreferencesUtils.getInstance(context).setValue("token",token);
                                SharedPreferencesUtils.getInstance(context).setValue("tokenExpiry",tokenExpiry);
                                SharedPreferencesUtils.getInstance(context).setValue("istoken",istoken);
                                Log.d("tokensentLogIn",String.valueOf(istoken));
                            }


                            presenter.onLoginResponse(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError",error.toString());

                String jsonMessage = null;
                try
                {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    JSONArray errors = data.getJSONArray("errors");
                    jsonMessage = errors.get(0).toString();




                }
                catch (JSONException e) {
                } catch (UnsupportedEncodingException errorr) {
                }

                presenter.onError(jsonMessage);

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
