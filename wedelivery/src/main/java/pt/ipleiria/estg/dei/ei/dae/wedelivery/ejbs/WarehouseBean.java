package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Warehouse;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.List;


@Stateless
public class WarehouseBean {
    @PersistenceContext
    private EntityManager entityManager;

    //String name, String address, String city, String postalCode
    public void create(String name, String address, String city, String postalCode)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {
        try {
            if (exists(name)) {
                throw new MyEntityExistsException(
                        "warehouse with name '" + name + "' already exists");
            }

            var warehouse = new Warehouse(name, address, city, postalCode);
            entityManager.persist(warehouse);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void update(Warehouse warehouse) {
        entityManager.merge(warehouse);  // Atualiza o warehouse na bd
    }

    public List<Warehouse> findAll() {
        // remember, maps to: “SELECT s FROM warehouse s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllWarehouses", Warehouse.class).getResultList();
    }

    public List<Warehouse> findByName(String name) {
        return entityManager.createNamedQuery("getWarehouseByName", Warehouse.class).setParameter("name", name).getResultList();
    }

    public Warehouse find(String name) {
        var warehouse = entityManager.find(Warehouse.class, name);
        if (warehouse == null) {
            throw new EJBException("Warehouse " + name + " not found");
        }
        return warehouse;
    }

    public boolean exists(String name) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(w.name) FROM Warehouse w WHERE w.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long)query.getSingleResult() > 0L;
    }


}
