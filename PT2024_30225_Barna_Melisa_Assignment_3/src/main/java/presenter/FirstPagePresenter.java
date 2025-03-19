package presenter;

import presenter.interfata.FirstPageI;
import view.FirstPage;

public class FirstPagePresenter implements FirstPageI {
    private FirstPage view;
    public FirstPagePresenter(FirstPage view) {
        this.view = view;
    }

    @Override
    public void showView() {
        view.setVisible(true);
    }
}
