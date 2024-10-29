package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;

@Table(name = "warehouses")
@NamedQueries({
        @NamedQuery(
                name = "getAllWarehouses",
                query = "SELECT w FROM Warehouse w"
        ),
        @NamedQuery(
                name = "getWarehouseByName",
                query = "SELECT w FROM Warehouse w WHERE w.name = :name"
        )
})
@Entity
public class Warehouse {
    @Id
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String city;
    //@NotNull
    //private String country;
    @NotNull
    private String postalCode;
    @OneToMany(mappedBy = "warehouse",fetch = FetchType.EAGER)
    private List<Product> products;
    @Version
    private int version;

    public Warehouse() {
        products = new LinkedList<>();
    }

    public Warehouse(String name, String address, String city, String postalCode) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        products = new LinkedList<>();
    }

    public String getName() {return name;}
    public String getAddress() {return address;}
    public String getCity() {return city;}
    public String getPostalCode() {return postalCode;}
    public List<Product> getProducts() {return products;}


    public void setName(String name) {this.name = name;}
    public void setAddress(String address) {this.address = address;}
    public void setCity(String city) {this.city = city;}
    public void setPostalCode(String postalCode) {this.postalCode = postalCode;}
    public void setProducts(List<Product> products) {this.products = products;}

    public void addProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
            product.setWarehouse(this); // Atualiza o armazém do produto
        }
    }

    public void removeProduct(Product product) {
        if (products.contains(product)) {
            products.remove(product);
            product.setWarehouse(null); // Limpa a referência ao armazém no produto
        }
    }
}
