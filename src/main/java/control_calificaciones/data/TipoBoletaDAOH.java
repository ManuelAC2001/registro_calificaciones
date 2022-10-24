package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.TipoBoletaH;

public class TipoBoletaDAOH extends GenericDAO {

    public List<TipoBoletaH> listar() {
        String consulta = "SELECT tipoBoleta FROM TipoBoletaH tipoBoleta";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(TipoBoletaH tipoBoleta) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(tipoBoleta);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void actualizar(TipoBoletaH tipoBoleta) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(tipoBoleta);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(TipoBoletaH tipoBoleta) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(tipoBoleta));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public TipoBoletaH buscarPorId(TipoBoletaH tipoBoleta) {
        entityManager = getEntityManager();
        return entityManager.find(TipoBoletaH.class, tipoBoleta.getIdTipoBoleta());
    }
    
}
