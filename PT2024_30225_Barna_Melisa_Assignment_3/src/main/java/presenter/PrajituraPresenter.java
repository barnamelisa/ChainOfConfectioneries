package presenter;

import model.Prajitura;
import model.repository.CakeRepository;
import presenter.interfata.PrajituraI;

import java.time.LocalDate;
import java.util.List;

public class PrajituraPresenter {
    private final CakeRepository dataAccess;
    private final PrajituraI view;

    public PrajituraPresenter(PrajituraI view) {
        this.view = view;
        this.dataAccess = CakeRepository.getInstance();
    }
    public Prajitura findById(int id, String primaryKeyColumnName){
        return dataAccess.findById(id,primaryKeyColumnName);
    }
    public Prajitura insertPrajitura(Prajitura prajitura) {
        return dataAccess.insert(prajitura);
    }

    public boolean updatePrajitura(Prajitura prajitura) {
        return dataAccess.update(prajitura);
    }

    public boolean deletePrajitura(Prajitura prajitura, String columnName) {
        return dataAccess.delete(prajitura.getId(),columnName);
    }
    public String displayObjectsFromDatabase(String tableName){
        return dataAccess.displayObjectsFromDatabase(tableName);
    }
    public void savePrajituraToDoc(List<Prajitura> prajituri, String fileName){
        dataAccess.savePrajituraToDoc(prajituri,fileName);
    }
    public String findCakeByName(String cakeName){
        return dataAccess.findCakeByName(cakeName);
    }
    public List<Prajitura> findCakesByAvailability(int idCofetarie){
        return dataAccess.findCakesByAvailability(idCofetarie);
    }
    public List<Prajitura> findExpiredOrOutOfStockCakes(int cofetarieId,LocalDate currentDate){
        return dataAccess.findExpiredOrOutOfStockCakes(cofetarieId,currentDate);
    }
    public void createCSV(List<Prajitura> expiredOrOutOfStockCakes){
        dataAccess.createCSV(expiredOrOutOfStockCakes);
    }
    public List<Prajitura> findCakesByValidity(int idCofetarie, LocalDate currentDate){
        return dataAccess.findCakesByValidity(idCofetarie,currentDate);
    }
    public boolean checkCofetarieExists(int cofetarieId){
        return dataAccess.checkCofetarieExists(cofetarieId);
    }
    public void showMessage(String message) {
        view.displayMessage(message);
    }
}
