package Modelo.Conexion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexion {

    private static EntityManager em;
    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("biblioteca");
        }
        if (em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }
}