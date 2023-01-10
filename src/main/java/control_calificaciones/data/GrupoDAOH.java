package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.GrupoH;

public class GrupoDAOH extends GenericDAO{

    public List<GrupoH> listar() {
        String consulta = "SELECT grupo FROM GrupoH grupo";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(GrupoH grupo) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(grupo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void actualizar(GrupoH grupo) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(grupo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(GrupoH grupo) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(grupo));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public GrupoH buscarPorId(GrupoH grupo) {
        entityManager = getEntityManager();
        return entityManager.find(GrupoH.class, grupo.getIdGrupo());
    }
    
}
