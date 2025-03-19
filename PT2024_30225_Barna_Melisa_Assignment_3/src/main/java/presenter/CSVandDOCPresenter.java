package presenter;

import presenter.interfata.CSVandDOCI;
import view.CSVandDOC;

public class CSVandDOCPresenter implements CSVandDOCI {
    private CSVandDOC view;
    public CSVandDOCPresenter(CSVandDOC view) {
        this.view = view;
    }
    @Override
    public String getText() {
        return view.getTextFromField();
    }
}
