package control_calificaciones.data;

import java.util.List;
import javax.persistence.Query;
import control_calificaciones.models.AlumnoH;

public class AlumnoDAOH extends GenericDAO{

    public List<AlumnoH> listar() {
        String consulta = "SELECT alumno FROM AlumnoH alumno";
        entityManager = getEntityManager();
        Query query = entityManager.createQuery(consulta);
        return query.getResultList();
    }

    public void insertar(AlumnoH alumno) {

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(alumno);
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

    public void actualizar(AlumnoH alumno) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(alumno);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void eliminar(AlumnoH alumno) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(alumno));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public AlumnoH buscarPorId(AlumnoH alumno){
        entityManager = getEntityManager();
        return entityManager.find(AlumnoH.class, alumno.getCurp());
    }
}
