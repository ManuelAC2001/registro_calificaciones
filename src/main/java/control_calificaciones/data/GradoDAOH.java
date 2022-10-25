package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.GradoH;

public class GradoDAOH extends GenericDAO{

    public List<GradoH> listar() {
        String consulta = "SELECT grado FROM GradoH grado";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(GradoH grado) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(grado);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void actualizar(GradoH grado) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(grado);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(GradoH grado) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(grado));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public GradoH buscarPorId(GradoH grado) {
        entityManager = getEntityManager();
        return entityManager.find(GradoH.class, grado.getIdGrado());
    }
    
}
