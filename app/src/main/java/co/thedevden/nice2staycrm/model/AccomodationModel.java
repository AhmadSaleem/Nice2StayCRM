package co.thedevden.nice2staycrm.model;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.thedevden.nice2staycrm.MainActivity;
import co.thedevden.nice2staycrm.adapter.Accomodation_Adapter;
import co.thedevden.nice2staycrm.connector.AccomodationPresenterToModel;
import co.thedevden.nice2staycrm.connector.AccomodationToPresenter;
import co.thedevden.nice2staycrm.utils.Config;

public class AccomodationModel implements AccomodationPresenterToModel {
    private int id;
    private String name;
    private int persons_number;
    private String country_name;
    private String region_name;
    private String type;
    private boolean listed_to;
    private String created_at;
    private String updated_at;

    AccomodationToPresenter presenter;
    private JsonArrayRequest jsonArrayRequest;
    Context context;
    private List<AccomodationModel> myaccomodationList;
    String token;
    RequestQueue requestQueue;

    public AccomodationModel(Context context,AccomodationToPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
        myaccomodationList = new ArrayList<AccomodationModel>();
        token = SharedPreferencesUtils.getInstance(context).getStringValue("token",null);
    }





    // Getter Methods

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPersons_number() {
        return persons_number;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public String getType() {
        return type;
    }

    public boolean getListed_to() {
        return listed_to;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersons_number(int persons_number) {
        this.persons_number = persons_number;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setListed_to(boolean listed_to) {
        this.listed_to = listed_to;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public void showAccomodations() {

        myaccomodationList.clear();
        jsonArrayRequest = new JsonArrayRequest(Config.URL_ACCOMODATIONS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Code",response.toString());
                //Toast.makeText(MainActivity.this, "in response", Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject();


                for (int i=0;i<response.length();i++)
                {
                    try
                    {
                        jsonObject = response.getJSONObject(i);

                        AccomodationModel accomodation = new AccomodationModel(context,presenter);
                        accomodation.setId(jsonObject.getInt("id"));
                        accomodation.setName(jsonObject.getString("name"));
                        accomodation.setPersons_number(jsonObject.getInt("persons_number"));
                        accomodation.setCountry_name(jsonObject.getString("country_name"));
                        accomodation.setRegion_name(jsonObject.getString("region_name"));
                        accomodation.setType(jsonObject.getString("type"));
                        accomodation.setListed_to(jsonObject.getBoolean("listed_to"));

                        myaccomodationList.add(accomodation);



                    }
                    catch (JSONException e )
                    {
                        e.printStackTrace();
                    }
                    presenter.showLayout(myaccomodationList);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.d("ERRORRRRR" +
                        "",error.toString());

                presenter.showErrors(error.toString());

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
}
