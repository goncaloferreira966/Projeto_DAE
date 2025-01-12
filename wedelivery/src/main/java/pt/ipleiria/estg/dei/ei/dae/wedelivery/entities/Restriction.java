package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Table(name = "restrictions")
@NamedQueries(
        {
                @NamedQuery(
                        name = "getAllRestrictions",
                        query = "SELECT r FROM Restriction r"
                ),
                @NamedQuery(
                        name = "getRestrictionById",
                        query = "SELECT r FROM Restriction r WHERE r.id = :id"
                ),
                @NamedQuery(
                        name = "getAllRestrictionsByProduct",
                        query = "SELECT r FROM Restriction r JOIN r.products p WHERE p.id = :id"
                )
        }
)
@Entity
public class Restriction {
    @Id
    private long id;
    @NotNull
    private String type;
    private double maxValue;
    private double minValue;
    @ManyToMany
    @JoinTable(
            name = "restriciton_product",
            joinColumns = @JoinColumn(
                    name = "restriction_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "id"
            )
    )
    private List<Product> products;
    @Version
    private int version;

    public Restriction() {
        this.products = new ArrayList<>();
    }

    public Restriction(long id, String type, double maxValue, double minValue) {
        this.id = id;
        this.type = type;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.products = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        if(!this.products.contains(product)){
            this.products.add(product);
        }
    }

    public void removeProduct(Product product) {
        if(this.products.contains(product)){
            this.products.remove(product);
        }
    }
}
