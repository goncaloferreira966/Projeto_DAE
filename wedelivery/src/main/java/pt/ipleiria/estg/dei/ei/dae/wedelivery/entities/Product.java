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
                    query = "SELECT p FROM Product p WHERE p.name = :name"
            )
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


    @ManyToMany(mappedBy = "products")
    private List<Warehouse> warehouses;



    public Product() {
        warehouses = new LinkedList<>();
    }

    public Product(long id, String name, String description, double price, String image, int quantity,boolean available, boolean haveSensor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.available = available;
        this.haveSensor = haveSensor;
        this.warehouses = new LinkedList<>();
    }

    public long getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public double getPrice() {return price;}
    public String getImage() {return image;}
    public int getQuantity() {return quantity;}
    public boolean isAvailable() {return available;}
    public boolean getHaveSensor() {return haveSensor;}
    public List<Warehouse> getWarehouse() {return warehouses;}

    public void setId(long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setPrice(double price) {this.price = price;}
    public void setImage(String image) {this.image = image;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setAvailable(boolean available) {this.available = available;}
    public void setHaveSensor(boolean haveSensor) {this.haveSensor = haveSensor;}
    public void setWarehouse(List<Warehouse> warehouse) {this.warehouses = warehouse;}



    public void addWarehouse(Warehouse warehouse) {
        if (!warehouses.contains(warehouse)) {
          warehouses.add(warehouse);
          warehouse.addProduct(this);
        }
    }
    public void removeWarehouse(Warehouse warehouse) {
        if (this.warehouses.contains(warehouse)) {
            this.warehouses.remove(warehouse);
            warehouse.removeProduct(this);
        }
    }
}
