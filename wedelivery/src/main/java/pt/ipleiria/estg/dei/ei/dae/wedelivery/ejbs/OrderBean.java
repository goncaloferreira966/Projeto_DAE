package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Order;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ClientBean clientBean;

    public void create(long code, Date deliveryDate, Date purchaseDate, String username, String state) {
        var client = clientBean.find(username);
        var order = new Order(deliveryDate, purchaseDate, client, code, state);
        entityManager.persist(order);
    }

    public List<Order> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public List<Order> findOrdersByClientId(String username) {
        return entityManager.createNamedQuery("getAllOrdersByClient", Order.class)
                .setParameter("username", username)
                .getResultList();
    }

    public Order find(long code) {
        var order = entityManager.find(Order.class, code);
        if (order == null) {
            throw new RuntimeException("order " + code + " not found");
        }
        return order;
    }
}
