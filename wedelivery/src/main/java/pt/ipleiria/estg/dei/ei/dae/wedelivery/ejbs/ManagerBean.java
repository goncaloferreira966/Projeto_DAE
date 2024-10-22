package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Manager;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;

import java.util.List;

@Stateless
public class ManagerBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String email, String name, String password, String username) {
        if (exists(username)) {
            throw new MyEntityExistsException(
                    "Client with username '" + username + "' already exists");
        }

        var manager = new Manager(email, name, password, username);
        entityManager.persist(manager);
    }

    public List<Manager> findAll() {
        // remember, maps to: “SELECT s FROM client s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllManagers", Manager.class).getResultList();
    }

    public Manager find(String username) {
        var manager = entityManager.find(Manager.class, username);
        if (manager == null) {
            throw new RuntimeException("manager " + username + " not found");
        }
        return manager;
    }

    public boolean exists(String username) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(m.username) FROM Manager m WHERE m.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }
}
