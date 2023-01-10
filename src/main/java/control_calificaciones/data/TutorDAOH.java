package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.TutorH;

public class TutorDAOH extends GenericDAO{

    public List<TutorH> listar() {
        String consulta = "SELECT tutor FROM TutorH tutor";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(TutorH tutor) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(tutor);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void actualizar(TutorH tutor) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(tutor);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(TutorH tutor) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(tutor));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public TutorH buscarPorId(TutorH tutor) {
        entityManager = getEntityManager();
        return entityManager.find(TutorH.class, tutor.getIdTutor());
    }
    
}
