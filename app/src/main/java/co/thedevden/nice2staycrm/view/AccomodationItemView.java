package co.thedevden.nice2staycrm.view;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.model.SharedPreferencesUtils;

public class AccomodationItemView extends AppCompatActivity {

    String name,type,country,region;
    int persons;
    boolean listed_To;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation_item);


        id = getIntent().getExtras().getInt("accomo_Id");
        name = getIntent().getExtras().getString("accomo_Name");
        type = getIntent().getExtras().getString("accomo_Type");
        country  = getIntent().getExtras().getString("accomo_Country");
        region  = getIntent().getExtras().getString("accomo_Region");
        listed_To   = getIntent().getExtras().getBoolean("accomo_Listed_To");
        persons  = getIntent().getExtras().getInt("accomo_PersonNo");

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
