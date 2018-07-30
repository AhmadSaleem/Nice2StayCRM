package co.thedevden.nice2staycrm.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.model.SharedPreferencesUtils;
import co.thedevden.nice2staycrm.utils.ConnectivityReceiver;

public class AccomodationItemView extends AppCompatActivity {

    String name,type,country,region;
    int persons;
    boolean listed_To;
    int id;

    AlertDialog.Builder builder;
    BroadcastReceiver broadcastReceiver;
    View view,view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = getLayoutInflater().inflate(R.layout.activity_accomodation_item,null);
        view2 = getLayoutInflater().inflate(R.layout.no_internet_found,null);
        setContentView(view);

        id = getIntent().getExtras().getInt("accomo_Id");
        name = getIntent().getExtras().getString("accomo_Name");
        type = getIntent().getExtras().getString("accomo_Type");
        country  = getIntent().getExtras().getString("accomo_Country");
        region  = getIntent().getExtras().getString("accomo_Region");
        listed_To   = getIntent().getExtras().getBoolean("accomo_Listed_To");
        persons  = getIntent().getExtras().getInt("accomo_PersonNo");

        builder = new AlertDialog.Builder(this);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.CollapsingToolBarLayout);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView accomo_Name= (TextView) findViewById(R.id.accomodation_Name);
        TextView accomo_Country = (TextView) findViewById(R.id.accomodation_Country);
        TextView accomo_Type = (TextView) findViewById(R.id.accomodation_Type);
        TextView accomo_Region= (TextView) findViewById(R.id.region);
        TextView accomo_personsNo = (TextView) findViewById(R.id.personsNumber);
        TextView accomo_ListedTo = (TextView) findViewById(R.id.listedTo);



        accomo_Name.setText(name);
        accomo_Country.setText("Country: "+ country);
        accomo_Region.setText("Region: "+ region);
        accomo_Type.setText("Type: "+type);
        accomo_personsNo.setText("Persons: "+String.valueOf(persons));
        accomo_ListedTo.setText("Listed_to: "+String.valueOf(listed_To));

        collapsingToolbarLayout.setTitle(name);

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
    protected void onResume() {
        checkInternet();
        super.onResume();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
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

    public void updateAccomodation(View view) {

        SharedPreferencesUtils.getInstance(this).setValue("update",true);

        Intent intent = new Intent(AccomodationItemView.this,AddAccomodationView.class);
        intent.putExtra("name",name);
        intent.putExtra("type",type);
        intent.putExtra("country",country);
        intent.putExtra("region",region);
        intent.putExtra("persons",persons);
        intent.putExtra("listed_to",listed_To);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
