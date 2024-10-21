package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Warehouse;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private WarehouseBean warehouseBean;
    //(long id, String name, String description, double price, String image, int quantity,boolean available, boolean haveSensor, warehouse warehouse)
    public void create(long id, String name, String description, double price, String image, int quantity, boolean available, boolean haveSensor, String warehouseName) {
        var warehouse = warehouseBean.find(warehouseName);
        var product = new Product(id, name, description, price, image, quantity, available, haveSensor, warehouse);
        entityManager.persist(product);
    }

    public List<Product> findAll() {
        // remember, maps to: “SELECT s FROM product s ORDER BY s.name”
        var products = entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
        //products.forEach(product -> Hibernate.initialize(product.getWarehouse()));
        return products;
    }

    public Product findById(long id) {
        var product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new RuntimeException("product " + id + " not found");
        }
        return product;
    }

    public List<Product> findByName(String name) {
        var products = entityManager.find(Product.class, name);
        if (products == null) {
            throw new RuntimeException("product " + name + " not found");
        }
        return List.of(products);
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
    public Product findInWarehouse(long id){
        var product = this.findById(id);
        Hibernate.initialize(product.getWarehouse());
        return product;
    }

}
