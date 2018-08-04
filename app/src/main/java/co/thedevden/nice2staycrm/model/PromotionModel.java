package co.thedevden.nice2staycrm.model;

import android.content.Context;
import android.util.Log;

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

import co.thedevden.nice2staycrm.connector.AccomodationToPresenter;
import co.thedevden.nice2staycrm.connector.PromotionToPresenter;
import co.thedevden.nice2staycrm.connector.PromotionsPresenterToModel;
import co.thedevden.nice2staycrm.utils.Config;

public class PromotionModel implements PromotionsPresenterToModel {


    private int id;
    private String from;
    private String to;
    private int value;
    private String valid_to;
    private String discount_type;
    private boolean publish;
    private String short_desc;
    private String description;
    private String updated_at;
    private String created_at;

    PromotionToPresenter presenter;
    Context context;

    private JsonArrayRequest jsonArrayRequest;
    private List<PromotionModel> mypromotionslist;
    String token;
    RequestQueue requestQueue;

    public PromotionModel(PromotionToPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
        mypromotionslist = new ArrayList<PromotionModel>();
        token = SharedPreferencesUtils.getInstance(context).getStringValue("token",null);
    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getValue() {
        return value;
    }

    public String getValid_to() {
        return valid_to;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public boolean getPublish() {
        return publish;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public void showPromotions() {

        mypromotionslist.clear();
        jsonArrayRequest = new JsonArrayRequest(Config.URL_PROMOTIONS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Code",response.toString());

                //Toast.makeText(MainActivity.this, "in response", Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject();

                Log.d("responselength",String.valueOf(response.length()));

                if(response.length()>0)
                {
                    for (int i=0;i<response.length();i++)
                    {
                        try
                        {
                            jsonObject = response.getJSONObject(i);

                            PromotionModel promotion = new PromotionModel(presenter,context);
                            promotion.setId(jsonObject.getInt("id"));
                            promotion.setFrom(jsonObject.getString("from"));
                            promotion.setTo(jsonObject.getString("to"));
                            promotion.setValue(jsonObject.getInt("value"));
                            promotion.setValid_to(jsonObject.getString("valid_to"));
                            promotion.setDescription(jsonObject.getString("description"));


                            mypromotionslist.add(promotion);



                        }
                        catch (JSONException e )
                        {
                            e.printStackTrace();
                        }



                    }
                    presenter.showLayout(mypromotionslist);
                }
                else
                {
                    presenter.noPromotions();

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
