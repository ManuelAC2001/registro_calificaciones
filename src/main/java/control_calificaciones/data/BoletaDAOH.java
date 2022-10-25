package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.BoletaH;

public class BoletaDAOH extends GenericDAO{

    public List<BoletaH> listar() {
        String consulta = "SELECT boleta FROM BoletaH boleta";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(BoletaH boleta) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(boleta);
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

    public void actualizar(BoletaH boleta) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(boleta);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(BoletaH boleta) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(boleta));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public BoletaH buscarPorId(BoletaH boleta){
        entityManager = getEntityManager();
        return entityManager.find(BoletaH.class, boleta.getFolio());
    }


    
}
