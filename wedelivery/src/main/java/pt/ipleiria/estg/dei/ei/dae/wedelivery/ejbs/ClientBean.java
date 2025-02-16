package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.security.Hasher;

import java.util.List;

@Stateless
public class ClientBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public void create(String username, String password, String name, String email, int nif, String postalCode, String country, String city, String address)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException {
        try {
            if (exists(username)) {
                throw new MyEntityExistsException(
                        "Client with username '" + username + "' already exists");
            }

            var client = new Client(email, name, hasher.hash(password), username, nif, postalCode, city, country, address);
            entityManager.persist(client);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Client> findAll() {
        // remember, maps to: “SELECT s FROM client s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllClients", Client.class).getResultList();
    }

    public Client find(String username) {
        var client = entityManager.find(Client.class, username);
        if (client == null) {
            throw new RuntimeException("client " + username + " not found");
        }
        return client;
    }

    public Client findWithOrders(String username){
        var client = this.find(username);
        Hibernate.initialize(client.getOrders());
        return client;
    }

    public void update(Client client) {
        client.setPassword(hasher.hash(client.getPassword()));
        entityManager.merge(client);  // Atualiza o client na bd
    }

    public boolean exists(String username) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(c.username) FROM Client c WHERE c.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }
}
