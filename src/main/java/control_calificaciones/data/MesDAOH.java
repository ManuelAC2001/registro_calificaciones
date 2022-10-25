package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.MesH;

public class MesDAOH extends GenericDAO{

    public List<MesH> listar() {
        String consulta = "SELECT mes FROM MesH mes";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(MesH mes) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(mes);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void actualizar(MesH mes) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(mes);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(MesH mes) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(mes));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public MesH buscarPorId(MesH mes) {
        entityManager = getEntityManager();
        return entityManager.find(MesH.class, mes.getIdMes());
    }

}
