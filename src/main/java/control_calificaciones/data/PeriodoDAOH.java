package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.PeriodoH;

public class PeriodoDAOH extends GenericDAO{

    public List<PeriodoH> listar() {
        String consulta = "SELECT periodo FROM PeriodoH periodo";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(PeriodoH periodo) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(periodo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void actualizar(PeriodoH periodo) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(periodo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(PeriodoH periodo) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(periodo));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public PeriodoH buscarPorId(PeriodoH periodo) {
        entityManager = getEntityManager();
        return entityManager.find(PeriodoH.class, periodo.getIdPeriodo());
    }

    public PeriodoH buscarNombre(PeriodoH periodo) {
        String consulta = "SELECT p FROM PeriodoH p WHERE " + 
                          "p.nombre = " +"'"+ periodo.getNombre() +"'";
                
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);

        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (PeriodoH) query.getSingleResult();
    }
}
