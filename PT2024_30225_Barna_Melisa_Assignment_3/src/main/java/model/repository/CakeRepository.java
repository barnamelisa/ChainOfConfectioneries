package model.repository;

import model.Prajitura;

import java.io.Serializable;

public class CakeRepository extends Repository<Prajitura> implements Serializable {
    private static CakeRepository instance;

    private CakeRepository() {
        super();
    }

    public static CakeRepository getInstance() {
        if (instance == null) {
            synchronized (CakeRepository.class) {
                if (instance == null) {
                    instance = new CakeRepository();
                }
            }
        }
        return instance;
    }
}
