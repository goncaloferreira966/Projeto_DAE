package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Warehouse;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private WarehouseBean warehouseBean;

    @EJB
    private SupplierBean supplierBean;

    //(long id, String name, String description, double price, String image, int quantity,boolean available, boolean haveSensor, warehouse warehouse, Supplier supplier)
    public void create(long id, String name, String description, double price, String image, int quantity, boolean available, boolean haveSensor, String warehouseName, String supplierUsername)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {

        try {
            if (exists(id)) {
                throw new MyEntityExistsException(
                        "product with id '" + id + "' already exists");
            }

            var warehouse = warehouseBean.find(warehouseName);
            var supplier = supplierBean.find(supplierUsername);
            var product = new Product(id, name, description, price, image, quantity, available, haveSensor, warehouse, supplier);
            entityManager.persist(product);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }


    }

    public List<Product> findAll() {
        // remember, maps to: “SELECT s FROM product s ORDER BY s.name”
        var products = entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
        //products.forEach(product -> Hibernate.initialize(product.getWarehouse()));
        return products;
    }

    public Product findById(long id) {
        var product = entityManager.createNamedQuery("getProductById", Product.class).setParameter("id", id).getSingleResult();
        return product;
    }

    public List<Product> findByName(String name) {
        TypedQuery<Product> query = entityManager.createNamedQuery("getProductByName", Product.class);

        // Set the parameter with wildcards for LIKE
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public Product findSupplierProduct(String username) {
        var supplier = entityManager.find(Product.class, username);
        if (supplier == null) {
            throw new RuntimeException("supplier " + username + " not found");
        }
        return supplier;
    }

    public List<Product> findAllProductsBySupplierUsername(String username) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.supplier.username = :username",
                Product.class
        );
        query.setParameter("username", username);
        return query.getResultList();
    }

    public List<Product> findAllProductsByWarehouse(String name) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.warehouse.name = :name",
                Product.class
        );
        query.setParameter("name", name);
        return query.getResultList();
    }

    public void addProductToWarehouse(String name, long productId) {
        var warehouse = warehouseBean.find(name);
        var product = findById(productId);

        if(!warehouse.getProducts().contains(product)){
            warehouse.addProduct(product);
            product.addWarehouse(warehouse);
            entityManager.merge(warehouse);
            entityManager.merge(product);
        }
    }

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.id) FROM Product p WHERE p.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }
}
