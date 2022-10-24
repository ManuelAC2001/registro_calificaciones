package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.AsignaturaH;

public class AsignaturaDAOH extends GenericDAO{

    public List<AsignaturaH> listar() {
        String consulta = "SELECT asignatura FROM AsignaturaH asignatura";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(AsignaturaH asignatura) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(asignatura);
            entityManager.getTransaction().commit();
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        } 
        finally {
            if(entityManager != null){
                entityManager.close();
            }  
        }
    }

    public void actualizar(AsignaturaH asinatura) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(asinatura);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }


    public void eliminar(AsignaturaH asignatura) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(asignatura));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public AsignaturaH buscarPorId(AsignaturaH asignatura){
        entityManager = getEntityManager();
        return entityManager.find(AsignaturaH.class, asignatura.getTipoAsignatura());
    }
}
