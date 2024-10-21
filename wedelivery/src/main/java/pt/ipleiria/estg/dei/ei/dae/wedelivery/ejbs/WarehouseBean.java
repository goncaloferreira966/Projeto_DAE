package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Warehouse;

import java.util.List;


@Stateless
public class WarehouseBean {
    @PersistenceContext
    private EntityManager entityManager;

    //String name, String address, String city, String postalCode
    public void create(String name, String address, String city, String postalCode) {
        var warehouse = new Warehouse(name, address, city, postalCode);
        entityManager.persist(warehouse);
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




}
