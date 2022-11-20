package control_calificaciones.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public abstract class GenericDAO {

    protected static EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
    private static final String PERSISTENCE_UNIT = "hibernateJPA";

    public GenericDAO() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
    }

    protected EntityManager getEntityManager(){
        entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

}
