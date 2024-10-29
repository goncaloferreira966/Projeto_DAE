package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import java.io.Serializable;
import java.util.List;

public class OrderRequestDTO implements Serializable {
    private List<ProductDTO> productsDTOs;
    private double totalPrice;

    // Getters and Setters
    public List<ProductDTO> getProductsDTOs() {
        return productsDTOs;
    }

    public void setItems(List<ProductDTO> productsDTOs) {
        this.productsDTOs = productsDTOs;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

class ItemDTO {
    private boolean available;
    private String description;
    private boolean haveSensor;
    private int id;
    private String image;
    private String name;
    private double price;
    private int quantity;

    // Getters and Setters
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHaveSensor() {
        return haveSensor;
    }

    public void setHaveSensor(boolean haveSensor) {
        this.haveSensor = haveSensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
