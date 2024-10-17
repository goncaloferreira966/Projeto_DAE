package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;

import java.util.List;

@Stateless
public class AdministratorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String email, String name, String password, String username) {
        var administrator = new Administrator(email, name, password, username);
        entityManager.persist(administrator);
    }

    public List<Administrator> findAll() {
        // remember, maps to: “SELECT s FROM client s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllAdministrators", Administrator.class).getResultList();
    }

    public Administrator find(String username) {
        var admin = entityManager.find(Administrator.class, username);
        if (admin == null) {
            throw new RuntimeException("admin " + username + " not found");
        }
        return admin;
    }
}
