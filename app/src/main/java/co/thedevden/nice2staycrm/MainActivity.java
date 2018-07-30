package co.thedevden.nice2staycrm;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.thedevden.nice2staycrm.model.SharedPreferencesUtils;
import co.thedevden.nice2staycrm.service.RefreshToken;
import co.thedevden.nice2staycrm.view.AccomodationsView;
import co.thedevden.nice2staycrm.view.LogInView;
import co.thedevden.nice2staycrm.view.ProfileView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView navpersonName,navEmail;
    private String token;
    boolean istoken;
    ImageView settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationViewTitles = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationViewTitles.getHeaderView(0);
        navpersonName = (TextView) headerView.findViewById(R.id.personName);
        navEmail = (TextView) headerView.findViewById(R.id.personEmail);

        settings = (ImageView) headerView.findViewById(R.id.settingsnav);

        navpersonName.setText(SharedPreferencesUtils.getInstance(this).getStringValue("name",null)+" " + SharedPreferencesUtils.getInstance(this).getStringValue("surname",null));
        navEmail.setText(SharedPreferencesUtils.getInstance(this).getStringValue("personEmail",null));

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProfileView.class);
                startActivity(intent);

            }
        });

//        CircleImageView circleImage = (CircleImageView) headerView.findViewById(R.id.profile);
//
//        circleImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,ProfileView.class);
//                startActivity(intent);
//            }
//        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {

            SharedPreferencesUtils.getInstance(this).removeKey("token");
            SharedPreferencesUtils.getInstance(this).removeKey("istoken");

            istoken=false;


            Intent service = new Intent(MainActivity.this,RefreshToken.class);
            stopService(service);

            Intent intent = new Intent(MainActivity.this,LogInView.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();

        token = SharedPreferencesUtils.getInstance(this).getStringValue("token",null);

        navpersonName.setText(SharedPreferencesUtils.getInstance(this).getStringValue("name",null)+" " + SharedPreferencesUtils.getInstance(this).getStringValue("surname",null));
        navEmail.setText(SharedPreferencesUtils.getInstance(this).getStringValue("personEmail",null));
    }


    public void showAccomodations(View view) {

        Intent intent = new Intent(MainActivity.this, AccomodationsView.class);
        startActivity(intent);

    }
}
