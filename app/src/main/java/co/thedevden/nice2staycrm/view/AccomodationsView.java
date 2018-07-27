package co.thedevden.nice2staycrm.view;

import android.content.Intent;
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

public class AccomodationsView extends AppCompatActivity implements AccomodationPresenterToView {

    AccomodationToPresenter presenter;
    ProgressBar progressBar;
    Accomodation_Adapter adapter;
    private RecyclerView recyclerView;
    LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodations_view);

        presenter = new AccomodationPresenter(this,this);
        progressBar = (ProgressBar) findViewById(R.id.progressBarAccomodation);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAccomodation);


        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.showAccomodations();
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
