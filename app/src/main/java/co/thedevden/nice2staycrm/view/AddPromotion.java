package co.thedevden.nice2staycrm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import co.thedevden.nice2staycrm.MainActivity;
import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.model.SharedPreferencesUtils;
import co.thedevden.nice2staycrm.service.RefreshToken;

public class AddPromotion extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean istoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_pro);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_add_pro);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_add_pro);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_add_pro);
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


                Intent service = new Intent(AddPromotion.this,RefreshToken.class);
                stopService(service);

                Intent intent = new Intent(AddPromotion.this,LogInView.class);
                startActivity(intent);
                finish();

                break;

            case R.id.nav_accomodations:
                Intent myintent = new Intent(AddPromotion.this,Accomodations.class);
                startActivity(myintent);
                finish();
                break;

            case R.id.nav_home:
                Intent intent2 = new Intent(AddPromotion.this,MainActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.nav_promotions:
                Intent myintent2 = new Intent(AddPromotion.this,Promotions.class);
                startActivity(myintent2);
                finish();
                break;


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_add_pro);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
