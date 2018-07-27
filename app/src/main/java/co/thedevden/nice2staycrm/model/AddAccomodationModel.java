package co.thedevden.nice2staycrm.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NameList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.thedevden.nice2staycrm.connector.AddPresenterToModel;
import co.thedevden.nice2staycrm.connector.AddToPresenter;
import co.thedevden.nice2staycrm.utils.Config;

public class AddAccomodationModel implements AddPresenterToModel
{

    Context context;
    AddToPresenter presenter;
    String token;
    RequestQueue requestQueue;
    JsonArrayRequest jsonArrayRequest;
    ArrayList<Integer> countryIdList;
    ArrayList<String> countryNameList;
    ArrayList<Integer> regionIdList;
    ArrayList<String> regionNameList;


    public AddAccomodationModel(Context context, AddToPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
        token = SharedPreferencesUtils.getInstance(context).getStringValue("token",null);
    }

    @Override
    public void showCountries() {

        jsonArrayRequest = new JsonArrayRequest(Config.URL_COUNTRIES, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Code",response.toString());
                //Toast.makeText(MainActivity.this, "in response", Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject();

                countryIdList = new ArrayList<Integer>();
                countryNameList = new ArrayList<String>();

                countryIdList.add(12);
                countryNameList.add("Select Country...");

                for (int i=0;i<response.length();i++)
                {
                    try
                    {
                        jsonObject = response.getJSONObject(i);

                        countryIdList.add(jsonObject.getInt("id"));
                        countryNameList.add(jsonObject.getString("name"));

                    }
                    catch (JSONException e )
                    {
                        e.printStackTrace();
                    }

                }
                presenter.successCountries(countryIdList,countryNameList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.d("ERRORRRRR" +
                        "",error.toString());

                presenter.showError(error.toString());


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                headers.put("AUTH-TOKEN", token);



                return headers;
            }
        };


        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void showRegions() {

        jsonArrayRequest = new JsonArrayRequest(Config.URL_REGIONS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Code",response.toString());
                //Toast.makeText(MainActivity.this, "in response", Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject();

                regionIdList = new ArrayList<Integer>();
                regionNameList = new ArrayList<String>();

                regionIdList.add(12);
                regionNameList.add("Select Region...");

                for (int i=0;i<response.length();i++)
                {
                    try
                    {
                        jsonObject = response.getJSONObject(i);

                        regionIdList.add(jsonObject.getInt("id"));
                        regionNameList.add(jsonObject.getString("name"));

                    }
                    catch (JSONException e )
                    {
                        e.printStackTrace();
                    }

                }
                presenter.successRegions(regionIdList,regionNameList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.d("ERRORRRRR" +
                        "",error.toString());

                presenter.showError(error.toString());


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                headers.put("AUTH-TOKEN", token);



                return headers;
            }
        };


        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void showCategories() {


    }

    @Override
    public void createAccomodation(String name, String type, Integer noOfPersons, Integer country, Integer region, Integer category, boolean listedTo) {


        HashMap<String, JSONObject> params = new HashMap<String, JSONObject>();
        JSONObject json = new JSONObject();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",name);
            jsonObject.put("type",type);
            jsonObject.put("persons_number",noOfPersons);
            jsonObject.put("country_id",country);
            jsonObject.put("region_id",region);
            jsonObject.put("category_id",category);
            jsonObject.put("listed_to",listedTo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json.put("accommodation",jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("accommodation", jsonObject);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Config.URL_ACCOMODATIONS, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText( getApplicationContext(), "responseSignup", Toast.LENGTH_SHORT).show();
                        Log.d("ResponseCreate", response.toString());

                        try {

                            Log.d("CreatedResponse",response.toString());

                            String accomoName = response.getString("name");


                            presenter.successToCreate();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                presenter.showError(error.toString());

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
    public void updateAccomodation(String name, String type, Integer noOfPersons, Integer country, Integer region, Integer category, boolean listedTo,int id) {

        HashMap<String, JSONObject> params = new HashMap<String, JSONObject>();
        JSONObject json = new JSONObject();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",name);
            jsonObject.put("type",type);
            jsonObject.put("persons_number",noOfPersons);
            jsonObject.put("country_id",country);
            jsonObject.put("region_id",region);
            jsonObject.put("category_id",category);
            jsonObject.put("listed_to",listedTo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json.put("accommodation",jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("accommodation", jsonObject);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, Config.URL_UPDATE_ACCOMODATIONS+String.valueOf(id)+".json", json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText( getApplicationContext(), "responseSignup", Toast.LENGTH_SHORT).show();
                        Log.d("ResponseCreate", response.toString());

                        try {

                            Log.d("CreatedResponse",response.toString());

                            String accomoName = response.getString("name");


                            presenter.successToUpdate();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                presenter.showError(error.toString());

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


}
