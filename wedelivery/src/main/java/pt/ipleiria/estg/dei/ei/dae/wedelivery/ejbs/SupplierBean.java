package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Supplier;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class SupplierBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException {
        try {
            if (exists(username)) {
                throw new MyEntityExistsException(
                        "Supplier with username '" + username + "' already exists");
            }

            var supplier = new Supplier(email, name, password, username);
            entityManager.persist(supplier);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
    public Supplier find(String username) {
        var supplier = entityManager.find(Supplier.class, username);
        if (supplier == null) {
            throw new RuntimeException("supplier " + username + " not found");
        }
        return supplier;
    }
    public List<Supplier> findAll() {
        // remember, maps to: “SELECT s FROM supplier s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllSuppliers", Supplier.class).getResultList();
    }
    // this returns a supplier with all its products
    public Supplier findWithProducts(String username){
        var supplier = this.find(username);
        Hibernate.initialize(supplier.getProducts());
        return supplier;
    }

    public Supplier findWithProductId(String username, long id){
        var supplier = this.find(username);
        Hibernate.initialize(supplier.getProducts().stream().map( product -> product.getId() == id));
        return supplier;
    }


    public boolean exists(String username) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(s.username) FROM Supplier s WHERE s.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long) query.getSingleResult() > 0;
    }

    public void update(Supplier supplier) {
        entityManager.merge(supplier);  // Atualiza o supplier na bd
    }
}
