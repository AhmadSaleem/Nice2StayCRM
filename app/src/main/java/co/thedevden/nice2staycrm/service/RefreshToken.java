package co.thedevden.nice2staycrm.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import co.thedevden.nice2staycrm.model.SharedPreferencesUtils;
import co.thedevden.nice2staycrm.utils.Config;

public class RefreshToken extends Service {


    public RefreshToken() {
    }


    Long tokenExpiry;
    boolean istoken;
    String token;
    RequestQueue requestQueue;

    PowerManager pm;
    PowerManager.WakeLock wl;

    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {

            handler.postDelayed(periodicUpdate, 2 * 1000 - SystemClock.elapsedRealtime() % 1000);
            getCurrentTime();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        tokenExpiry = SharedPreferencesUtils.getInstance(this).getLongValue("tokenExpiry", 0);
        token = SharedPreferencesUtils.getInstance(this).getStringValue("token", null);

        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GpsTrackerWakelock");
        wl.acquire();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        tokenExpiry = SharedPreferencesUtils.getInstance(this).getLongValue("tokenExpiry", 0);
        token = SharedPreferencesUtils.getInstance(this).getStringValue("token", null);


        Log.d("ServiceStarted", "INSIDE START COMMAND");
        handler.post(periodicUpdate);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wl.release();
        handler.removeCallbacks(periodicUpdate);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void getCurrentTime() {
        Log.d("SERVICE", "Inside getcurrent TIme");

        tokenExpiry = SharedPreferencesUtils.getInstance(this).getLongValue("tokenExpiry", 0);
        token = SharedPreferencesUtils.getInstance(this).getStringValue("token", null);

        istoken = SharedPreferencesUtils.getInstance(this).getBoolanValue("istoken", false);


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        timestamp.setTime((timestamp.getTime() + (((5 * 60)) * 1000)) / 1000);

        Log.d("token Expiry", tokenExpiry.toString());
        Log.d("Timestamp", String.valueOf(timestamp.getTime()));


        if (timestamp.getTime() > tokenExpiry) {
            Log.d("insideIFOF Refresh", "Reached");
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, Config.LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("ResponseRefreshToken", response.toString());

                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());

//                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//                                tokenExpiry = timestamp.getTime();


                        tokenExpiry = Long.parseLong(jsonObject.get("token_expires_at").toString());

                        token = jsonObject.get("auth_token").toString();
                        Log.d("tokenRefreshIn", token);
                        istoken = true;


                        if (istoken) {

                            SharedPreferencesUtils.getInstance(getApplicationContext()).setValue("token", token);
                            SharedPreferencesUtils.getInstance(getApplicationContext()).setValue("tokenExpiry",tokenExpiry);
                            SharedPreferencesUtils.getInstance(getApplicationContext()).setValue("istoken",istoken);
                            Log.d("tokensentRefresh", String.valueOf(istoken));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("REfresh errror", error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("AUTH-TOKEN", token);
                    return headers;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }
    }
}
