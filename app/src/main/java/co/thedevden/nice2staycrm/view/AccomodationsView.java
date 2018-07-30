package co.thedevden.nice2staycrm.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.List;

import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.adapter.Accomodation_Adapter;
import co.thedevden.nice2staycrm.connector.AccomodationPresenterToView;
import co.thedevden.nice2staycrm.connector.AccomodationToPresenter;
import co.thedevden.nice2staycrm.model.AccomodationModel;
import co.thedevden.nice2staycrm.presenter.AccomodationPresenter;
import co.thedevden.nice2staycrm.utils.ConnectivityReceiver;

public class AccomodationsView extends AppCompatActivity implements AccomodationPresenterToView {

    AccomodationToPresenter presenter;
    ProgressBar progressBar;
    Accomodation_Adapter adapter;
    private RecyclerView recyclerView;
    LinearLayoutManager manager;


    AlertDialog.Builder builder;
    BroadcastReceiver broadcastReceiver;

    View view,view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = getLayoutInflater().inflate(R.layout.activity_accomodations_view,null);
        view2 = getLayoutInflater().inflate(R.layout.no_internet_found,null);
        setContentView(view);

        presenter = new AccomodationPresenter(this,this);
        progressBar = (ProgressBar) findViewById(R.id.progressBarAccomodation);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAccomodation);


        builder  =new AlertDialog.Builder(this);

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {

        checkInternet();
        presenter.showAccomodations();

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
    public void showLayout(List<AccomodationModel> list) {

        adapter= new Accomodation_Adapter(getApplicationContext(),list);
        manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    public void addAccomodation(View view) {

        Intent intent  = new Intent(AccomodationsView.this,AddAccomodationView.class);
        startActivity(intent);
    }
}
