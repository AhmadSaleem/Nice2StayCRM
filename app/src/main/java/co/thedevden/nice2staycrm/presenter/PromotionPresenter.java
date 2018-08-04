package co.thedevden.nice2staycrm.presenter;

import android.content.Context;

import java.util.List;

import co.thedevden.nice2staycrm.connector.AccomodationPresenterToModel;
import co.thedevden.nice2staycrm.connector.AccomodationPresenterToView;
import co.thedevden.nice2staycrm.connector.PromotionPresenterToView;
import co.thedevden.nice2staycrm.connector.PromotionToPresenter;
import co.thedevden.nice2staycrm.connector.PromotionsPresenterToModel;
import co.thedevden.nice2staycrm.model.AccomodationModel;
import co.thedevden.nice2staycrm.model.PromotionModel;

public class PromotionPresenter implements PromotionToPresenter {

    Context context;
    PromotionPresenterToView view;
    PromotionsPresenterToModel model;


    public PromotionPresenter(Context context, PromotionPresenterToView view) {
        this.context = context;
        this.view = view;
        this.model = new PromotionModel(this,context);
    }

    @Override
    public void showPromotions() {

        model.showPromotions();

    }

    @Override
    public void showErrors(String Message) {
        view.showError(Message);

    }

    @Override
    public void showLayout(List<PromotionModel> list) {

        view.showLayout(list);

    }

    @Override
    public void noPromotions() {

        view.noPromotions();
    }
}
