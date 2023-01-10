package control_calificaciones.data.usuarios;

import java.util.List;

import javax.persistence.Query;

import control_calificaciones.data.GenericDAO;
import control_calificaciones.models.usuarios.PrivilegioH;

public class PrivilegioDAOH extends GenericDAO {

    public List<PrivilegioH> getPrivilegios() {
        String consulta = "SELECT privilegio FROM PrivilegioH privilegio";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(PrivilegioH privilegio) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(privilegio);
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

    public void actualizar(PrivilegioH privilegio) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(privilegio);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(PrivilegioH privilegio) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(privilegio));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public PrivilegioH buscarPorId(PrivilegioH privilegio){
        entityManager = getEntityManager();
        return entityManager.find(PrivilegioH.class, privilegio.getIdPrivilegio());
    }
    
}
