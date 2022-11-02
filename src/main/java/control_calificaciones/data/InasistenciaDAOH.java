package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.InasistenciaH;

public class InasistenciaDAOH extends GenericDAO{

    public List<InasistenciaH> listar() {
        String consulta = "SELECT inasistencias FROM InasistenciaH inasistencias";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(InasistenciaH inacistencia) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(inacistencia);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        // finally {
        //     if (entityManager != null) {
        //         entityManager.close();
        //     }
        // }
    }

    public void actualizar(InasistenciaH inacistencia) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(inacistencia);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        // finally {
        //     if (entityManager != null) {
        //         entityManager.close();
        //     }
        // }
    }

    public void eliminar(InasistenciaH inacistencia) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(inacistencia));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public InasistenciaH buscarPorId(InasistenciaH inacistencia) {
        entityManager = getEntityManager();
        return entityManager.find(InasistenciaH.class, inacistencia.getIdInasistencia());
    }


    
}
