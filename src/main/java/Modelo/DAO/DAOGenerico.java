package Modelo.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import Modelo.Conexion.Conexion;

import java.util.List;

public class DAOGenerico<T, ID> {

    private EntityManager em; // Manejador de entidades
    private Class<T> entityClass; // Clase de la entidad manejada

    /**
     * Constructor que recibe la clase de la entidad a manejar.
     * @param entityClass Clase de la entidad.
     */
    public DAOGenerico(Class<T> entityClass) {
        this.em = Conexion.getEntityManager(); // Obtiene el EntityManager desde la conexión
        this.entityClass = entityClass;
    }

    /**
     * Agrega una entidad a la base de datos.
     * @param t Entidad a agregar.
     */
    public void add(T t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(t);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error al agregar entidad", e);
        }
    }

    /**
     * Actualiza una entidad en la base de datos.
     * @param t Entidad a actualizar.
     * @return Entidad actualizada.
     */
    public T update(T t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T merged = em.merge(t);
            tx.commit();
            return merged;
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error al actualizar entidad", e);
        }
    }

    /**
     * Elimina una entidad de la base de datos.
     * @param t Entidad a eliminar.
     */
    public void delete(T t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (!em.contains(t)) {
                t = em.merge(t); // Asegura que la entidad esté en el contexto de persistencia
            }
            em.remove(t);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error al eliminar entidad", e);
        }
    }

    /**
     * Busca una entidad por su ID.
     * @param id Identificador de la entidad.
     * @return Entidad encontrada o null si no existe.
     */
    public T find(ID id) {
        return em.find(entityClass, id);
    }

    /**
     * Obtiene todas las entidades del tipo manejado.
     * @return Lista de entidades.
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        try {
            return em.createQuery("SELECT t FROM " + entityClass.getSimpleName() + " t", entityClass).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las entidades", e);
        }
    }

    /**
     * Permite obtener un usuario por su email.
     * @param email Correo electrónico del usuario.
     * @return Lista de usuarios con el email proporcionado.
     */
    public List<T> findByEmail(String email) {
        try {
            String queryStr = "SELECT u FROM " + entityClass.getSimpleName() + " u WHERE u.email = :email";
            TypedQuery<T> query = em.createQuery(queryStr, entityClass);
            query.setParameter("email", email);
            return query.getResultList();  // Devuelve una lista de usuarios con el mismo email
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuarios por email", e);
        }
    }

    /**
     * Obtiene el EntityManager actual.
     * @return EntityManager utilizado por este DAO.
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * Permite configurar el EntityManager (opcional).
     * @param em EntityManager a utilizar.
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
