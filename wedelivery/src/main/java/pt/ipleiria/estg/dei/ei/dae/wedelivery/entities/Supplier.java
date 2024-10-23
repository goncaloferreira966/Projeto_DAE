package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSuppliers",
                query = "SELECT s FROM Supplier s ORDER BY s.name" // JPQL
        )
})
public class Supplier extends User implements Serializable {

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;
    public Supplier() {
    }

    public Supplier(String email, String name, String password, String username) {
        super(email, name, password, username);
        this.products = new LinkedList<>();
    }

    public List<Product> getProducts() {return products;}
    public void setProducts(List<Product> products) {this.products = products;}

    public void addProduct(Product product) {
        products.add(product);
    }
    public void removeProduct(Product product) {
        products.remove(product);
    }

}
