package co.thedevden.nice2staycrm.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import co.thedevden.nice2staycrm.MainActivity;
import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.model.SharedPreferencesUtils;
import co.thedevden.nice2staycrm.service.RefreshToken;
import co.thedevden.nice2staycrm.utils.ConnectivityReceiver;

public class AccomodationsItem extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String name,type,country,region;
    int persons;
    boolean listed_To;
    int id;
    boolean istoken;
    AlertDialog.Builder builder;
    BroadcastReceiver broadcastReceiver;
    View view,view2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_accomodations_item,null);
        view2 = getLayoutInflater().inflate(R.layout.no_internet_found,null);
        setContentView(view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_item);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_item);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_item);
        navigationView.setNavigationItemSelectedListener(this);

        id = getIntent().getExtras().getInt("accomo_Id");
        name = getIntent().getExtras().getString("accomo_Name");
        type = getIntent().getExtras().getString("accomo_Type");
        country  = getIntent().getExtras().getString("accomo_Country");
        region  = getIntent().getExtras().getString("accomo_Region");
        listed_To   = getIntent().getExtras().getBoolean("accomo_Listed_To");
        persons  = getIntent().getExtras().getInt("accomo_PersonNo");

        builder = new AlertDialog.Builder(this);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.CollapsingToolBarLayout_Item);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView accomo_Name= (TextView) findViewById(R.id.accomodation_Name_Item);
        TextView accomo_Country = (TextView) findViewById(R.id.accomodation_Country_Item);
        TextView accomo_Type = (TextView) findViewById(R.id.accomodation_Type_Item);
        TextView accomo_Region= (TextView) findViewById(R.id.region_Item);
        TextView accomo_personsNo = (TextView) findViewById(R.id.personsNumber_Item);
        TextView accomo_ListedTo = (TextView) findViewById(R.id.listedTo_Item);



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

        Intent intent = new Intent(AccomodationsItem.this,AddAccomodations.class);
        intent.putExtra("name",name);
        intent.putExtra("type",type);
        intent.putExtra("country",country);
        intent.putExtra("region",region);
        intent.putExtra("persons",persons);
        intent.putExtra("listed_to",listed_To);
        intent.putExtra("id",id);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_item);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.nav_logout:
                SharedPreferencesUtils.getInstance(this).removeKey("token");
                SharedPreferencesUtils.getInstance(this).removeKey("istoken");

                istoken=false;


                Intent service = new Intent(AccomodationsItem.this,RefreshToken.class);
                stopService(service);

                Intent intent = new Intent(AccomodationsItem.this,LogInView.class);
                startActivity(intent);
                finish();

                break;

            case R.id.nav_accomodations:
                Intent myintent = new Intent(AccomodationsItem.this,Accomodations.class);
                startActivity(myintent);
                finish();
                break;

            case R.id.nav_home:
                Intent intent2 = new Intent(AccomodationsItem.this,MainActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.nav_promotions:
                Intent myintent2 = new Intent(AccomodationsItem.this,Promotions.class);
                startActivity(myintent2);
                finish();
                break;






        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_item);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
