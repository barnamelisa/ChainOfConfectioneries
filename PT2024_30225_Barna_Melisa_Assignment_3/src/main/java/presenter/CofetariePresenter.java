package presenter;

import model.Cofetarie;
import model.repository.CofetarieRepository;

public class CofetariePresenter {
    private final CofetarieRepository dataAccess;

    public CofetariePresenter() {
        this.dataAccess = CofetarieRepository.getInstance();
    }

    public Cofetarie findById(int id, String primaryKeyColumnName){
        return dataAccess.findById(id,primaryKeyColumnName);
    }
    public boolean updateStock(int prajituraId, int cofetarieId, int newStock){
        return dataAccess.updateStock(prajituraId,cofetarieId,newStock);
    }
    public Cofetarie insertCofetarie(Cofetarie cofetarie) {
        return dataAccess.insert(cofetarie);
    }

    public boolean updateCofetarie(Cofetarie cofetarie) {
        return dataAccess.update(cofetarie);
    }

    public boolean deleteCofetarieById(int id, String columnName) {
        return dataAccess.delete(id, columnName);
    }
    public boolean doesCofetarieExist(int id) {
        return dataAccess.findById(id, "id_cofetarie") != null;
    }
    public String displayObjectsFromDatabase(String tableName){
        return dataAccess.displayObjectsFromDatabase(tableName);
    }

}


