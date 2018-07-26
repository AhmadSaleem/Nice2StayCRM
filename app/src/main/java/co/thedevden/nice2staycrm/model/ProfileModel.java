package co.thedevden.nice2staycrm.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.thedevden.nice2staycrm.connector.ProfilePresenterToModel;
import co.thedevden.nice2staycrm.connector.ProfileToPresenter;
import co.thedevden.nice2staycrm.utils.Config;

public class ProfileModel implements ProfilePresenterToModel {

    Context context;
    ProfileToPresenter presenter;
    String token;
    Long tokenExpiry;
    boolean istoken;
    RequestQueue requestQueue;

    public ProfileModel(Context context, ProfileToPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
        token = SharedPreferencesUtils.getInstance(context).getStringValue("token",null);

    }

    @Override
    public void editProfile(String firstname, String surname, final String email, String bussiness, String pass, String confirmPass) {


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

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, Config.SIGNUP_URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText( getApplicationContext(), "responseSignup", Toast.LENGTH_SHORT).show();
                        Log.d("ResponseProfile", response.toString());

                        try {


                            String name,surname;
                            name = response.get("name").toString();
                            surname = response .get("surname").toString();


                            SharedPreferencesUtils.getInstance(context).setValue("name",name);
                            SharedPreferencesUtils.getInstance(context).setValue("surname",surname);
                            SharedPreferencesUtils.getInstance(context).setValue("personEmail",email);

                            tokenExpiry = Long.parseLong(response.get("token_expires_at").toString());
//

                            token = response.get("auth_token").toString();

                            istoken=true;




                            if(istoken)
                            {

                                SharedPreferencesUtils.getInstance(context).setValue("token",token);
                                SharedPreferencesUtils.getInstance(context).setValue("istoken",istoken);
                                SharedPreferencesUtils.getInstance(context).setValue("tokenExpiry",tokenExpiry);

                                Log.d("tokensentProfile",String.valueOf(istoken));
                            }

                            presenter.editSuccess();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                presenter.editError(error.toString());



            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("AUTH-TOKEN", token);
                return headers;
            }
        };

        //Queue.getInstance(SignUp.this).addtoRequestQueue(req);
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(req);



    }

    @Override
    public void viewProfile() {

        final ArrayList<String> user = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.SIGNUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    user.add(jsonObject.getString("name"));
                    user.add(jsonObject.getString("surname"));
                    user.add(jsonObject.getString("email"));
                    user.add(jsonObject.getString("business_name"));

                    presenter.viewSuccess(user);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Profile View errror",error.toString());
                presenter.editError(error.toString());

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("AUTH-TOKEN", token);
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
