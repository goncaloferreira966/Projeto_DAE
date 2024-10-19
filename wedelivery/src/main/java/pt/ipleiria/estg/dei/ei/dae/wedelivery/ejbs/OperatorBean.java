package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Operator;

import java.util.List;

@Stateless
public class OperatorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String email, String name, String password, String username) {
        var administrator = new Operator(email, name, password, username);
        entityManager.persist(administrator);
    }

    public List<Operator> findAll() {
        // remember, maps to: “SELECT s FROM client s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllOperators", Operator.class).getResultList();
    }

    public Operator find(String username) {
        var admin = entityManager.find(Operator.class, username);
        if (admin == null) {
            throw new RuntimeException("admin " + username + " not found");
        }
        return admin;
    }
}
