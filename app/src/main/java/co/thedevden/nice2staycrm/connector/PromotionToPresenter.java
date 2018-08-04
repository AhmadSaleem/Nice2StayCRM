package co.thedevden.nice2staycrm.connector;

import java.util.List;

import co.thedevden.nice2staycrm.model.AccomodationModel;
import co.thedevden.nice2staycrm.model.PromotionModel;

public interface PromotionToPresenter {

    void showPromotions();
    void  showErrors(String Message);
    void showLayout(List<PromotionModel> list);
    void noPromotions();
}
