package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.ejb.EJB;
import jakarta.enterprise.inject.Default;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;
@Table(name = "products")
@NamedQueries(
        {
            @NamedQuery(
                    name = "getAllProducts",
                    query = "SELECT p FROM Product p"
            ),
            @NamedQuery(
                    name = "getProductByName",
                    query = "SELECT p FROM Product p WHERE p.name LIKE :name"
            ),
            @NamedQuery(
                    name = "getProductById",
                    query = "SELECT p FROM Product p WHERE p.id =:id"
            ),
            @NamedQuery(
                    name = "getAllProductsByVolume",
                    query = "SELECT p FROM Product p JOIN p.volumes v WHERE v.id = :id"
            ),
        }
)
@Entity
public class Product {
    @Id
    private long id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private double price;
    private String image;
    private int quantity;
    @NotNull
    private boolean available;
    private boolean haveSensor = false;
    @ManyToOne
    @JoinColumn(name = "warehouse_name", referencedColumnName = "name")
    private Warehouse warehouse;
    @ManyToOne
    @JoinTable(
            name = "supplier_product",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "supplier_username",
                    referencedColumnName = "username"
            )
    )
    private Supplier supplier;
    @ManyToMany(mappedBy = "products")
    private List<Volume> volumes;
    @ManyToMany(mappedBy = "products")
    private List<Restriction> restrictions;
    @Version
    private int version;

    public Product() {
        this.restrictions = new LinkedList<>();
    }

    public Product(long id, String name, String description, double price, String image, int quantity,boolean available, boolean haveSensor, Warehouse warehouse, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.available = available;
        this.haveSensor = haveSensor;
        this.warehouse = warehouse;
        this.supplier = supplier;
        this.volumes = new LinkedList<>();
        this.restrictions = new LinkedList<>();
    }

    public long getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public double getPrice() {return price;}
    public String getImage() {return image;}
    public int getQuantity() {return quantity;}
    public boolean isAvailable() {return available;}
    public boolean getHaveSensor() {return haveSensor;}
    public Warehouse getWarehouse() {return warehouse;}
    public Supplier getSupplier() {return supplier;}

    public boolean isHaveSensor() {
        return haveSensor;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public List<Volume> getVolumes() {
        if (this.volumes == null)
            throw new RuntimeException("Product " + id + " don't have volumes");
        return volumes;
    }

    public void setId(long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setPrice(double price) {this.price = price;}
    public void setImage(String image) {this.image = image;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setAvailable(boolean available) {this.available = available;}
    public void setHaveSensor(boolean haveSensor) {this.haveSensor = haveSensor;}
    public void setWarehouse(Warehouse warehouse) {
        if (this.warehouse != null) {
            this.warehouse.removeProduct(this); // Remove do armazém atual
        }
        this.warehouse = warehouse;
        if (warehouse != null) {
            warehouse.addProduct(this); // Adiciona ao novo armazém
        }
    }
    public void setSupplier(Supplier supplier) {
        if (this.supplier != null) {
            this.supplier.removeProduct(this); // Remove do fornecedor atual
        }
        this.supplier = supplier;
        if (supplier != null) {
            supplier.addProduct(this); // Adiciona ao novo fornecedor
        }
    }

    public void setVolumes(List<Volume> volumes) {
        if (this.volumes == null)
            this.volumes = new LinkedList<>();
        this.volumes = volumes;
    }

    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    /*********** Warehouse methods ***************/
    public void addWarehouse(Warehouse warehouse) {
        if (!this.warehouse.equals(warehouse)) {
            this.warehouse = warehouse;
            warehouse.addProduct(this);
        }
    }
    public void removeWarehouse(Warehouse warehouse) {
        if (this.warehouse.equals(warehouse)) {
            this.warehouse = null;
            warehouse.removeProduct(this);
        }
}

    /********** Supplier methods ***************/
    public void addSupplier(Supplier supplier) {
        if (!this.supplier.equals(supplier)) {
            this.supplier = supplier;
            supplier.addProduct(this);
        }
    }
    public void removeSupplier(Supplier supplier) {
        if (this.supplier.equals(supplier)) {
            this.supplier = null;
            supplier.removeProduct(this);
        }
    }

    /********** Volume methods ***************/
    public void addVolume(Volume volume) {
        if (!this.volumes.contains(volume)) {
            this.volumes.add(volume);
            volume.addProduct(this);
        }
    }
    public void removeVolume(Volume volume) {
        if (!this.volumes.contains(volume)) {
            throw new RuntimeException("Volume " + volume.getId() + " don't exist in product " + id);
        }
        this.volumes.remove(volume);
        volume.removeProduct(this);
    }

    /*****************  Restrictions Methods   ******************/
    public void addRestriction(Restriction restriction) {
        if (!this.restrictions.contains(restriction)){
            this.restrictions.add(restriction);
            restriction.addProduct(this);
        }
    }
    public void removeRestriction(Restriction restriction) {
        if (!restrictions.contains(restriction))
            throw new RuntimeException("restriction " + restriction.getId() + " don't exist in volume " + id);
        restrictions.remove(restriction);
        restriction.removeProduct(this);
    }
}
