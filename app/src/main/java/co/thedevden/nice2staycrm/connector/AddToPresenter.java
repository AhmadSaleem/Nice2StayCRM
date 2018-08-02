package co.thedevden.nice2staycrm.connector;

import java.util.ArrayList;

public interface AddToPresenter {

    void showCountries();
    void showRegions();
    void showCategories();
    void successCountries(ArrayList<Integer> ids,ArrayList<String> names);
    void successRegions(ArrayList<Integer> ids, ArrayList<String> names);
    void successCategories();
    void showError(String message);
    void createAccomodation(String name,String type,String noOfPersons,Integer country,Integer region,Integer category,boolean listedTo);
    void successToCreate();
    void updateAccomodation(String name,String type,String noOfPersons,Integer country,Integer region,Integer category,boolean listedTo,int id);
    void  successToUpdate();
}
