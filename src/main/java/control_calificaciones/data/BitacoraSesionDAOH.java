package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.BitacoraSesionH;

public class BitacoraSesionDAOH extends GenericDAO{

    public List<BitacoraSesionH> listar() {
        String consulta = "SELECT bitacora FROM BitacoraSesionH bitacora";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(BitacoraSesionH bitacora) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(bitacora);
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

    public void actualizar(BitacoraSesionH bitacora) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(bitacora);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    
    public void eliminar(BitacoraSesionH bitacora) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(bitacora));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public BitacoraSesionH buscarPorId(BitacoraSesionH bitacora){
        entityManager = getEntityManager();
        return entityManager.find(BitacoraSesionH.class, bitacora.getIdBitacoraUsuario());
    }
    
}
