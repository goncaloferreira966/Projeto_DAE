package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {

    private long id;
    private String name;
    private String description;
    private double price;
    private String image;
    private int quantity;
    private boolean available;
    private boolean haveSensor = false;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, String description, double price, String image, int quantity, boolean available, boolean haveSensor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.available = available;
        this.haveSensor = haveSensor;
    }

    public long getId() {return id;}

    public String getName() {return name;}

    public String getDescription() {return description;}

    public double getPrice() {return price;}

    public String getImage() {return image;}

    public int getQuantity() {return quantity;}

    public boolean isAvailable() {return available;}

    public boolean getHaveSensor() {return haveSensor;}


    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImage(),
                product.getQuantity(),
                product.isAvailable(),
                product.getHaveSensor()
        );
    }

    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
    }


}
