package co.thedevden.nice2staycrm.presenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

import co.thedevden.nice2staycrm.connector.AddPresenterToModel;
import co.thedevden.nice2staycrm.connector.AddPresenterToView;
import co.thedevden.nice2staycrm.connector.AddToPresenter;
import co.thedevden.nice2staycrm.model.AddAccomodationModel;

public class AddAccomodationPresenter implements AddToPresenter {

    Context context;
    AddPresenterToView view;
    AddPresenterToModel model;

    public AddAccomodationPresenter(Context context, AddPresenterToView view) {
        this.context = context;
        this.view = view;
        this.model = new AddAccomodationModel(context,this);
    }

    @Override
    public void showCountries() {
        model.showCountries();
    }

    @Override
    public void showRegions() {

        model.showRegions();
    }

    @Override
    public void showCategories() {

    }

    @Override
    public void successCountries(ArrayList<Integer> ids, ArrayList<String> names) {

        view.successCountries(ids,names);
    }

    @Override
    public void successRegions(ArrayList<Integer> ids, ArrayList<String> names) {

        view.successRegions(ids,names);
    }

    @Override
    public void successCategories() {

    }

    @Override
    public void showError(String message) {
        view.showError(message);
    }

    @Override
    public void createAccomodation(String name, String type, String noOfPersons, Integer country, Integer region, Integer category, boolean listedTo) {

        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(type)||TextUtils.isEmpty(noOfPersons))
        {
            if(TextUtils.isEmpty(name))
            {
                view.nameError();
            }
            if(TextUtils.isEmpty(type))
            {
                view.typeError();

            }
            if(TextUtils.isEmpty(noOfPersons))
            {
                view.noOfPersonsError();

            }

        }
        else
        {
            model.createAccomodation(name,type,Integer.parseInt(noOfPersons),country,region,category,listedTo);
        }



    }

    @Override
    public void successToCreate() {

        view.successToCreate();
    }

    @Override
    public void updateAccomodation(String name, String type, String noOfPersons, Integer country, Integer region, Integer category, boolean listedTo,int id) {

        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(type)||TextUtils.isEmpty(noOfPersons))
        {
            if(TextUtils.isEmpty(name))
            {
                view.nameError();
            }
            if(TextUtils.isEmpty(type))
            {
                view.typeError();

            }
            if(TextUtils.isEmpty(noOfPersons))
            {
                view.noOfPersonsError();

            }

        }
        else
        {
            model.updateAccomodation(name,type,Integer.parseInt(noOfPersons),country,region,category,listedTo,id);
        }
    }

    @Override
    public void successToUpdate() {

        view.successToUpdate();
    }


}
