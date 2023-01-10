package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.CicloEscolarH;

public class CicloEscolarDAOH extends GenericDAO{

    public List<CicloEscolarH> listar() {
        String consulta = "SELECT ciclo FROM CicloEscolarH ciclo";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(CicloEscolarH ciclo) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(ciclo);
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

    public void actualizar(CicloEscolarH ciclo) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(ciclo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(CicloEscolarH ciclo) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(ciclo));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public CicloEscolarH buscarPorId(CicloEscolarH ciclo){
        entityManager = getEntityManager();
        return entityManager.find(CicloEscolarH.class, ciclo.getIdCicloEscolar());
    }

}
