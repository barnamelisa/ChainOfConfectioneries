package presenter;

import presenter.interfata.CofetarieViewI;
import view.CofetarieView;

public class CofetarieViewPresenter implements CofetarieViewI {

    private CofetarieView view;
    public CofetarieViewPresenter(CofetarieView view){
        this.view = view;
    }
    @Override
    public void showViewCofetarie() {
        view.setVisible(true);
    }
}
