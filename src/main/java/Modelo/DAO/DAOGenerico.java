package Modelo.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import Modelo.Conexion.Conexion;

import java.util.List;

public class DAOGenerico<T, ID> {

    private EntityManager em;
    private Class<T> entityClass;

    public DAOGenerico(Class<T> entityClass) {
        this.em = Conexion.getEntityManager();
        this.entityClass = entityClass;
    }

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

    public void delete(T t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (!em.contains(t)) {
                t = em.merge(t);
            }
            em.remove(t);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error al eliminar entidad", e);
        }
    }

    public T find(ID id) {
        return em.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        try {
            return em.createQuery("SELECT t FROM " + entityClass.getSimpleName() + " t", entityClass).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las entidades", e);
        }
    }

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

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
