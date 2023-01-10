package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.TipoAsignaturaH;

public class TipoAsignaturaDAOH extends GenericDAO {

    public List<TipoAsignaturaH> listar() {
        String consulta = "SELECT tipoAsignatura FROM TipoAsignaturaH tipoAsignatura";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(TipoAsignaturaH tipoAsignatura) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(tipoAsignatura);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void actualizar(TipoAsignaturaH tipoAsignatura) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(tipoAsignatura);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(TipoAsignaturaH tipoAsignatura) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(tipoAsignatura));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public TipoAsignaturaH buscarPorId(TipoAsignaturaH tipoAsignatura) {
        entityManager = getEntityManager();
        return entityManager.find(TipoAsignaturaH.class, tipoAsignatura.getIdTipoAsignatura());
    }
    
}
