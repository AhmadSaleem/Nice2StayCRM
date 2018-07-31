package co.thedevden.nice2staycrm.connector;

import java.util.List;

import co.thedevden.nice2staycrm.model.AccomodationModel;

public interface AccomodationPresenterToView {

    void showError(String message);
    void showLayout(List<AccomodationModel> list);
    void noAccomodations();
}
