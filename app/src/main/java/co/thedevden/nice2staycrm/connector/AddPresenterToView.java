package co.thedevden.nice2staycrm.connector;

import java.util.ArrayList;

public interface AddPresenterToView {

    void successCountries(ArrayList<Integer> ids, ArrayList<String> names);
    void successRegions(ArrayList<Integer> ids, ArrayList<String> names);
    void successCategories();
    void showError(String message);
    void successToCreate();
    void nameError();
    void typeError();
    void noOfPersonsError();
    void  successToUpdate();
}
