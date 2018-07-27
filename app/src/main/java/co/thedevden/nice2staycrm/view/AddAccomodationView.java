package co.thedevden.nice2staycrm.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.connector.AddPresenterToView;
import co.thedevden.nice2staycrm.connector.AddToPresenter;
import co.thedevden.nice2staycrm.model.SharedPreferencesUtils;
import co.thedevden.nice2staycrm.presenter.AddAccomodationPresenter;

public class AddAccomodationView extends AppCompatActivity implements AddPresenterToView{

    EditText name_Edt_Txt,type_Edt_Txt,person_Edt_Txt;
    Spinner country_spinner,region_spinner,category_spinner;
    Switch listed_switch;
    Integer selectedCountry;
    Integer selectedRegion;
    Integer selectedCategory;
    AddToPresenter presenter;
    LinearLayout addAccomodation;
    ProgressBar progressBar;
    TextView topTv,buttonText;
    ArrayList<Integer> countryids;
    ArrayList<Integer> regionids;
    ArrayList<String> countryNames;
    ArrayList<String> regionNames;
    String coun,reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_accomodation);

        name_Edt_Txt = (EditText) findViewById(R.id.accomo_Name_Add);
        type_Edt_Txt = (EditText) findViewById(R.id.accomo_Type_Add);
        person_Edt_Txt = (EditText) findViewById(R.id.noOFPersons);
        country_spinner = (Spinner) findViewById(R.id.country);
        region_spinner = (Spinner) findViewById(R.id.region);
        category_spinner = (Spinner) findViewById(R.id.category);
        listed_switch = (Switch) findViewById(R.id.listtedToSwitch);
        addAccomodation = (LinearLayout) findViewById(R.id.addAccomodation);
        progressBar = (ProgressBar) findViewById(R.id.addAccProgressBar);
        topTv = (TextView) findViewById(R.id.textViewTop);
        buttonText = (TextView) findViewById(R.id.buttonTextView);
        listed_switch.setTextOff("False");
        listed_switch.setTextOn("True");
        presenter = new AddAccomodationPresenter(this,this);
        countryids = new ArrayList<Integer>();
        regionids = new ArrayList<Integer>();
        countryNames = new ArrayList<String>();
        regionNames = new ArrayList<String>();



        if(SharedPreferencesUtils.getInstance(this).getBoolanValue("update",false))
        {
            topTv.setText("Update Accomodation");
            buttonText.setText("Update");

            final int id = getIntent().getExtras().getInt("id");
            name_Edt_Txt.setText(getIntent().getExtras().getString("name"));
            type_Edt_Txt.setText(getIntent().getExtras().getString("type"));
            person_Edt_Txt.setText(String.valueOf(getIntent().getExtras().getInt("persons")));
            coun = getIntent().getExtras().getString("country");
            reg = getIntent().getExtras().getString("region");
            boolean list = getIntent().getExtras().getBoolean("listed_to");
            listed_switch.setChecked(list);

            addAccomodation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progressBar.setVisibility(View.VISIBLE);
                    String name,type;
                    String noOfPersons;
                    boolean listedTo=false;

                    name = name_Edt_Txt.getText().toString();
                    type = type_Edt_Txt.getText().toString();
                    noOfPersons = person_Edt_Txt.getText().toString();
                    listedTo = listed_switch.isChecked();


                    presenter.updateAccomodation(name,type,noOfPersons,selectedCountry,selectedRegion,2,listedTo,id);
                }
            });



            presenter.showCountries();
            presenter.showRegions();

        }
        else
        {
            addAccomodation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progressBar.setVisibility(View.VISIBLE);
                    String name,type;
                    String noOfPersons;
                    boolean listedTo=false;

                    name = name_Edt_Txt.getText().toString();
                    type = type_Edt_Txt.getText().toString();
                    noOfPersons = person_Edt_Txt.getText().toString();
                    listedTo = listed_switch.isChecked();


                    presenter.createAccomodation(name,type,noOfPersons,selectedCountry,selectedRegion,2,listedTo);
                }
            });




            presenter.showCountries();
            presenter.showRegions();

        }






    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferencesUtils.getInstance(this).setValue("update",false);
        topTv.setText("Add Accomodation");
        buttonText.setText("Create");

    }

    @Override
    public void successCountries(final ArrayList<Integer> ids, ArrayList<String> names) {



        countryids = ids;
        countryNames = names;

        Log.d("names",names.toString());

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };

        countryAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        country_spinner.setAdapter(countryAdapter);

        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               // String item = adapterView.getItemAtPosition(i).toString();

                selectedCountry = ids.get(i);
                //Toast.makeText(adapterView.getContext(), "Selected: " + String.valueOf(ids.get(i)), Toast.LENGTH_LONG).show();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(SharedPreferencesUtils.getInstance(this).getBoolanValue("update",false))
        {
            country_spinner.setSelection(countryAdapter.getPosition(coun));
        }


    }

    @Override
    public void successRegions(final ArrayList<Integer> ids, ArrayList<String> names) {

        regionids = ids;
        regionNames = names;

        Log.d("names",names.toString());

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        regionAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        region_spinner.setAdapter(regionAdapter);

        region_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // String item = adapterView.getItemAtPosition(i).toString();

                selectedRegion = ids.get(i);
                //Toast.makeText(adapterView.getContext(), "Selected: " + String.valueOf(ids.get(i)), Toast.LENGTH_LONG).show();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(SharedPreferencesUtils.getInstance(this).getBoolanValue("update",false))
        {
            region_spinner.setSelection(regionAdapter.getPosition(reg));
        }

    }

    @Override
    public void successCategories() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successToCreate() {
        progressBar.setVisibility(View.GONE);
        name_Edt_Txt.setText("");
        type_Edt_Txt.setText("");
        person_Edt_Txt.setText("");
        listed_switch.setChecked(false);
        Toast.makeText(this, "Accomodation Created Successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void nameError() {
        progressBar.setVisibility(View.GONE);
        name_Edt_Txt.setError("Enter Name Here");
    }

    @Override
    public void typeError() {
        progressBar.setVisibility(View.GONE);
        type_Edt_Txt.setError("Enter Type Here");
    }

    @Override
    public void noOfPersonsError() {
        progressBar.setVisibility(View.GONE);
        person_Edt_Txt.setError("Enter Persons Here");
    }

    @Override
    public void successToUpdate() {
        progressBar.setVisibility(View.GONE);
        name_Edt_Txt.setText("");
        type_Edt_Txt.setText("");
        person_Edt_Txt.setText("");
        listed_switch.setChecked(false);
        Toast.makeText(this, "Accomodation Updated Successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddAccomodationView.this,AccomodationsView.class);
        startActivity(intent);
        finish();

    }
}
