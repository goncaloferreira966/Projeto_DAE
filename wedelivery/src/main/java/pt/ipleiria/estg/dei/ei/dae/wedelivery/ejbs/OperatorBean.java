package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Manager;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class OperatorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String email, String name, String password, String username)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException {

        try {
            if (exists(username)) {
                throw new MyEntityExistsException(
                        "Client with username '" + username + "' already exists");
            }

            var operator = new Operator(email, name, password, username);
            entityManager.persist(operator);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void update(Operator operator) {
        entityManager.merge(operator);  // Atualiza o operator na bd
    }

    public List<Operator> findAll() {
        // remember, maps to: “SELECT s FROM client s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllOperators", Operator.class).getResultList();
    }

    public Operator find(String username) {
        var admin = entityManager.find(Operator.class, username);
        if (admin == null) {
            throw new RuntimeException("operator " + username + " not found");
        }
        return admin;
    }

    public boolean exists(String username) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(o.username) FROM Operator o WHERE o.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }
}
