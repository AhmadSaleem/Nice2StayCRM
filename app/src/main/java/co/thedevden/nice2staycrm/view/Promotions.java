package co.thedevden.nice2staycrm.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.thedevden.nice2staycrm.MainActivity;
import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.adapter.Accomodation_Adapter;
import co.thedevden.nice2staycrm.adapter.PromotionAdapter;
import co.thedevden.nice2staycrm.connector.AccomodationToPresenter;
import co.thedevden.nice2staycrm.connector.PromotionPresenterToView;
import co.thedevden.nice2staycrm.connector.PromotionToPresenter;
import co.thedevden.nice2staycrm.model.AccomodationModel;
import co.thedevden.nice2staycrm.model.PromotionModel;
import co.thedevden.nice2staycrm.model.SharedPreferencesUtils;
import co.thedevden.nice2staycrm.presenter.AccomodationPresenter;
import co.thedevden.nice2staycrm.presenter.PromotionPresenter;
import co.thedevden.nice2staycrm.service.RefreshToken;
import co.thedevden.nice2staycrm.utils.ConnectivityReceiver;

public class Promotions extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,PromotionPresenterToView {


    boolean istoken;
    PromotionToPresenter presenter;
    ProgressBar progressBar;
    PromotionAdapter adapter;
    private RecyclerView recyclerView;
    LinearLayoutManager manager;
    TextView noPromotions;

    AlertDialog.Builder builder;
    BroadcastReceiver broadcastReceiver;

    View view,view2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = getLayoutInflater().inflate(R.layout.activity_promotions,null);
        view2 = getLayoutInflater().inflate(R.layout.no_internet_found,null);
        setContentView(view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pro);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_pro);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_pro);
        navigationView.setNavigationItemSelectedListener(this);

        presenter = new PromotionPresenter(this,this);

        progressBar = (ProgressBar) findViewById(R.id.progressBarPromotions);

        noPromotions = (TextView) findViewById(R.id.tvPromotions);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewPromotions);


        builder  =new AlertDialog.Builder(this);

        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onResume() {


        checkInternet();
        presenter.showPromotions();
        super.onResume();


    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
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
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }



    @Override
    public void showLayout(List<PromotionModel> list) {

        adapter= new PromotionAdapter(getApplicationContext(),list);
        manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void noPromotions() {
        progressBar.setVisibility(View.GONE);
        noPromotions.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_pro);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
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


                Intent service = new Intent(Promotions.this,RefreshToken.class);
                stopService(service);

                Intent intent = new Intent(Promotions.this,LogInView.class);
                startActivity(intent);
                finish();

                break;

            case R.id.nav_accomodations:
                Intent myintent = new Intent(Promotions.this,Accomodations.class);
                startActivity(myintent);
                finish();
                break;

            case R.id.nav_home:
                Intent intent2 = new Intent(Promotions.this,MainActivity.class);
                startActivity(intent2);
                finish();
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_pro);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addAccomodationPromotion(View view) {

        Intent intent  = new Intent(Promotions.this,AddPromotion.class);
        startActivity(intent);
    }


}
