package presenter;

import presenter.interfata.CSVandDOCI;
import model.Prajitura;
import presenter.interfata.PrajituraI;

import java.time.LocalDate;
import java.util.List;

public class CSVandDOCPresenter {
    private CSVandDOCI view;
    private PrajituraPresenter prajituraPresenter;

    public CSVandDOCPresenter(CSVandDOCI view) {
        this.view = view;
        this.prajituraPresenter = new PrajituraPresenter(new PrajituraI() {
            @Override
            public void showView() {

            }

            @Override
            public void displayMessage(String message) {

            }
        });
    }

    public void showView() {
        view.showView();
    }

    public String getCofetarieId() {
        return view.getCofetarieId();
    }

    public void processCofetarieId(String cofetarieIdText) {
        try {
            int cofetarieId = Integer.parseInt(cofetarieIdText);

            boolean cofetarieExists = prajituraPresenter.checkCofetarieExists(cofetarieId);
            if (!cofetarieExists) {
                view.showErrorMessage("Cofetăria cu ID-ul " + cofetarieId + " nu există!");
                return;
            }

            // Apelăm metoda care acum include filtrul pentru cofetărie
            List<Prajitura> prajituri = prajituraPresenter.findExpiredOrOutOfStockCakes(cofetarieId, LocalDate.now());

            if (prajituri.isEmpty()) {
                view.showInformationMessage("Nu există prăjituri expirate sau epuizate în stoc.");
                return;
            }

            prajituraPresenter.createCSV(prajituri);  // Creează fișierul CSV
            view.showInformationMessage("Fișierul CSV a fost creat cu succes!");
            prajituraPresenter.savePrajituraToDoc(prajituri, "Fisier doc");  // Creează fișierul DOC
            view.showInformationMessage("Fișierul doc a fost creat cu succes!");

        } catch (NumberFormatException ex) {
            view.showErrorMessage("ID-ul cofetăriei trebuie să fie un număr valid!");
        }
    }

}
