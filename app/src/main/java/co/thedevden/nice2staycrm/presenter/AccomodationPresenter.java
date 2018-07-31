package co.thedevden.nice2staycrm.presenter;

import android.content.Context;

import java.util.List;

import co.thedevden.nice2staycrm.connector.AccomodationPresenterToModel;
import co.thedevden.nice2staycrm.connector.AccomodationPresenterToView;
import co.thedevden.nice2staycrm.connector.AccomodationToPresenter;
import co.thedevden.nice2staycrm.model.AccomodationModel;

public class AccomodationPresenter implements AccomodationToPresenter {

    Context context;
    AccomodationPresenterToView view;
    AccomodationPresenterToModel model;

    public AccomodationPresenter(Context context, AccomodationPresenterToView view) {
        this.context = context;
        this.view = view;
        model = new AccomodationModel(context,this);
    }

    @Override
    public void showAccomodations() {
        model.showAccomodations();
    }

    @Override
    public void showErrors(String Message) {
        view.showError(Message);
    }

    @Override
    public void showLayout(List<AccomodationModel> list) {

        view.showLayout(list);
    }

    @Override
    public void noAccomodations() {
        view.noAccomodations();
    }
}
