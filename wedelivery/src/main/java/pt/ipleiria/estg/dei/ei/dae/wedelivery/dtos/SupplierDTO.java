package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Supplier;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierDTO implements Serializable {
    private String username;
    private String password;
    private String name;
    private String email;
    private List<ProductDTO> products;

    public SupplierDTO() {
        this.products = new LinkedList<>();
    }

    public SupplierDTO(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.products = new LinkedList<>();
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public List<ProductDTO> getProducts() {return products;}

    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setProducts(List<ProductDTO> products) {this.products = products;}

    public void addProduct(ProductDTO product) {products.add(product);}
    public void removeProduct(ProductDTO product) {products.remove(product);}


    public static SupplierDTO from(Supplier supplier) {
        return new SupplierDTO(
                supplier.getUsername(),
                supplier.getPassword(),
                supplier.getName(),
                supplier.getEmail()
        );
    }

    public static List<SupplierDTO> from(List<Supplier> suppliers) {
        return suppliers.stream().map(SupplierDTO::from).collect(Collectors.toList());
    }


}
