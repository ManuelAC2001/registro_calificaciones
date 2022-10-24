package control_calificaciones.data.usuarios;

import java.util.List;

import javax.persistence.Query;

import control_calificaciones.data.GenericDAO;
import control_calificaciones.models.usuarios.RolH;

public class RolDAOH extends GenericDAO{

    public List<RolH> listar() {
        String consulta = "SELECT rol FROM RolH rol";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(RolH rol) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(rol);
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

    public void actualizar(RolH rol) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(rol);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(RolH rol) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(rol));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public RolH buscarPorId(RolH rol){
        entityManager = getEntityManager();
        return entityManager.find(RolH.class, rol.getId_rol());
    }
}
