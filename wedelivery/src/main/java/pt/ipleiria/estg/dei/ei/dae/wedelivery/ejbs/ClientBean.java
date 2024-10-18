package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;

import java.util.List;

@Stateless
public class ClientBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email, int nif, String postalCode, String country, String city, String address) {
        var client = new Client(email, name, password, username, nif, postalCode, country, city, address);
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

}
