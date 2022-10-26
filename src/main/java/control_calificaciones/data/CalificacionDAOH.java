package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.CalificacionH;

public class CalificacionDAOH extends GenericDAO{

    public List<CalificacionH> listar() {
        String consulta = "SELECT calificacion FROM CalificacionH calificacion";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(CalificacionH calificacion) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(calificacion);
            entityManager.getTransaction().commit();
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        } 
        // finally {
        //     if(entityManager != null){
        //         entityManager.close();
        //     }  
        // }
    }

    public void actualizar(CalificacionH calificacion) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(calificacion);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(CalificacionH calificacion) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(calificacion));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public CalificacionH buscarPorId(CalificacionH calificacion){
        entityManager = getEntityManager();
        return entityManager.find(CalificacionH.class, calificacion.getIdCalificacion());
    }
}
