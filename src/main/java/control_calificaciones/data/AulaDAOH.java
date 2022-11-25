package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.AulaH;

public class AulaDAOH extends GenericDAO{

    public List<AulaH> listar() {
        String consulta = "SELECT aula FROM AulaH aula";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(AulaH aula) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(aula);
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

    public void actualizar(AulaH aula) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(aula);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(AulaH aula) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(aula));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public AulaH buscarPorId(AulaH aula){
        entityManager = getEntityManager();
        return entityManager.find(AulaH.class, aula.getIdAula());
    }
}
