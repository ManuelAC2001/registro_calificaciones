package control_calificaciones.data.usuarios;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.data.GenericDAO;
import control_calificaciones.models.usuarios.UsuarioH;

public class UsuarioDAOH extends GenericDAO {

    public List<UsuarioH> listar() {
        String consulta = "SELECT usuario FROM UsuarioH usuario";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(UsuarioH usuario) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(usuario);
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

    public void actualizar(UsuarioH usuario) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(UsuarioH usuario) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(usuario));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    
    public UsuarioH buscarPorId(UsuarioH usuario){
        entityManager = getEntityManager();
        return entityManager.find(UsuarioH.class, usuario.getIdUsuario());
    }

}
