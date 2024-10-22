package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;

import java.util.List;

@Stateless
public class ClientBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email, int nif, String postalCode, String country, String city, String address) {

        if (exists(username)) {
            throw new MyEntityExistsException(
                    "Client with username '" + username + "' already exists");
        }

        var client = new Client(email, name, password, username, nif, postalCode, city, country, address);
        entityManager.persist(client);
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

    public boolean exists(String username) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(c.username) FROM Client c WHERE c.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }
}
