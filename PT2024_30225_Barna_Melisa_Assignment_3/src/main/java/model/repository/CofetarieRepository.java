package model.repository;

import model.Cofetarie;

import java.io.Serializable;

public class CofetarieRepository extends Repository<Cofetarie> implements Serializable {
    private static CofetarieRepository instance;

    private CofetarieRepository() {
        super();
    }

    public static CofetarieRepository getInstance() {
        if (instance == null) {
            synchronized (CakeRepository.class) {
                if (instance == null) {
                    instance = new CofetarieRepository();
                }
            }
        }
        return instance;
    }
}
