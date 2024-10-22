package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ClientBean clientBean;

    @EJB
    private OperatorBean operatorBean;

    public void create(long code, Date deliveryDate, Date purchaseDate, String username, String usernameOperator, String state)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {
        try {
            var client = clientBean.find(username);
            var operator = operatorBean.find(usernameOperator);
            var order = new Order(deliveryDate, purchaseDate, client, operator, code, state);
            entityManager.persist(order);
            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
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

    public List<Order> findOrdersByOperatorId(String username) {
        return entityManager.createNamedQuery("getAllOrdersByOperator", Order.class)
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
