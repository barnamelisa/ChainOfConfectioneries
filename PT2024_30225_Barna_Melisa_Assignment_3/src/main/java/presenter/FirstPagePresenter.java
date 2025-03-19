package presenter;

import presenter.interfata.FirstPageI;

public class FirstPagePresenter{
    private FirstPageI view;
    public FirstPagePresenter(FirstPageI view) {
        this.view = view;
    }

    public void showView() {
        view.showView();
    }

    public void openCofetarieView() {
        view.openCofetarieView();
    }

    public void openPrajituraView() {
        view.openPrajituraView();
    }

    public void openCSVandDOCView() {
        view.openCSVandDOCView();
    }
}
