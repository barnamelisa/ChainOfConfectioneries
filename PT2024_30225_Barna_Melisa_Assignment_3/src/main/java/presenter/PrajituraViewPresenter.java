package presenter;

import presenter.interfata.PrajituraI;
import view.Prajitura;

public class PrajituraViewPresenter implements PrajituraI {
    private Prajitura view;
    public PrajituraViewPresenter(Prajitura view) {
        this.view = view;
    }

    @Override
    public void showView() {
        view.setVisible(true);
    }
}
