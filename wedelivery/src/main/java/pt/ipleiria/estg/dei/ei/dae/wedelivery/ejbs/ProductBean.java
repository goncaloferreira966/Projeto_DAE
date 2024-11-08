package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Supplier;
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

    @EJB
    private VolumeBean volumeBean;

    @EJB
    private RestrictionBean restrictionBean;

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

    /************************ Product *********************************************/
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
    public List<Product> findProductsByIds(List<Long> ids) {
        return entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.id IN :ids",
                Product.class
        ).setParameter("ids", ids).getResultList();
    }


    /************************ Product <---> Supplier ******************************/
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
    public Supplier findWithSupplier(long id){
        var product = this.findById(id);
        Hibernate.initialize(product.getSupplier());
        return product.getSupplier();
    }


    /************************ Product <---> Warehouse *****************************/
    public List<Product> findAllProductsByWarehouse(String name) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.warehouse.name = :name",
                Product.class
        );
        query.setParameter("name", name);
        return query.getResultList();
    }
    // addProductToWarehouse I don't know if it's necessary because the product only has one warehouse
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


    public Warehouse findWithWarehouse(long id){
        if (!exists(id)) {
            throw new MyEntityNotFoundException("Product with id " + id + " not found");
        }
        var product = this.findById(id);
        Hibernate.initialize(product.getWarehouse());
        return product.getWarehouse();
    }

    public void update(Product product) {
        entityManager.merge(product);  // Atualiza o produto na bd
    }


    /************************ Product <---> Volume ********************************/
    public void addProductInVolume(long productId, long volumeId, int quantity) {
        var product = findById(productId);
        var volume = volumeBean.find(volumeId);

        if (!product.getVolumes().contains(volume)) {
            product.addVolume(volume);
            volume.addProduct(product);
            int updateStock = product.getQuantity() - quantity;
            product.setQuantity(updateStock);
            entityManager.merge(product);
            entityManager.merge(volume);
        }
    }
    public void removeProductFromVolume(long productId, long volumeId) {
        var product = findById(productId);
        var volume = volumeBean.find(volumeId);

        if (product.getVolumes().contains(volume)) {
            product.removeVolume(volume);
            volume.removeProduct(product);
            entityManager.merge(product);
            entityManager.merge(volume);
        }
    }
    public Product findWithVolume(long id){
        var product = this.findById(id);
        Hibernate.initialize(product.getVolumes());
        return product;
    }
    public Product findWithVolumeId(long id, long volumeId){
        var product = this.findById(id);
        Hibernate.initialize(product.getVolumes().stream().map( volume -> volume.getId() == volumeId));
        return product;
    }
    public List<Product> findAllProductsByVolumeId(long id) {
        var products = entityManager.createNamedQuery("getAllProductsByVolume", Product.class)
                .setParameter("id", id)
                .getResultList();
        return products;
    }

    /*****************  Product -> Restriction  ***********************************/
    public void addRestrictionToProduct(long idProduct, long idRestriction) {
        var product = findById(idProduct);
        var restriction = restrictionBean.find(idRestriction);
        product.addRestriction(restriction);
    }
    public void removeRestrictionToProduct(long idProduct, long idRestriction) {
        var product = findById(idProduct);
        var restriction = restrictionBean.find(idRestriction);
        product.removeRestriction(restriction);
    }


    /*******************************************************************************/
    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.id) FROM Product p WHERE p.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }
}
