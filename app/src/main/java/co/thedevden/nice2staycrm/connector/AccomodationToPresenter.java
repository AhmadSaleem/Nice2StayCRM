package co.thedevden.nice2staycrm.connector;

import java.util.List;

import co.thedevden.nice2staycrm.model.AccomodationModel;

public interface AccomodationToPresenter {

    void showAccomodations();
    void  showErrors(String Message);
    void showLayout(List<AccomodationModel> list);

}
