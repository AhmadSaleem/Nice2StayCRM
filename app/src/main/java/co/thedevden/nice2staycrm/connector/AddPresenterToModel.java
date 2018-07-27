package co.thedevden.nice2staycrm.connector;

public interface AddPresenterToModel {

    void showCountries();
    void showRegions();
    void showCategories();
    void createAccomodation(String name,String type,Integer noOfPersons,Integer country,Integer region,Integer category,boolean listedTo);
    void updateAccomodation(String name,String type,Integer noOfPersons,Integer country,Integer region,Integer category,boolean listedTo,int id);

}
