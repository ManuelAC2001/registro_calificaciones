package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.CorreoTutorH;

public class CorreoTutorDAOH extends GenericDAO {

    public List<CorreoTutorH> listar() {
        String consulta = "SELECT correo FROM CorreoTutorH correo";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(CorreoTutorH correo) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(correo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // finally {
        // if (entityManager != null) {
        // entityManager.close();
        // }
        // }
    }

    public void actualizar(CorreoTutorH correo) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(correo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // finally {
        // if (entityManager != null) {
        // entityManager.close();
        // }
        // }
    }

    public void eliminar(CorreoTutorH correo) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(correo));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // finally {
        // if (entityManager != null) {
        // entityManager.close();
        // }
        // }
    }

    public CorreoTutorH buscarPorCorreo(CorreoTutorH correo) {
        String consulta = "SELECT c FROM CorreoTutorH c WHERE" +
                " c.correo = " + "'" + correo.getCorreo() + "'";

        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (CorreoTutorH) query.getSingleResult();
    }

    public CorreoTutorH buscarPorId(CorreoTutorH correo) {
        entityManager = getEntityManager();
        return entityManager.find(CorreoTutorH.class, correo.getIdCorreo());
    }

}
