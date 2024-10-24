package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Volume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO {
    private long id;
    private String state;
    private String creationDate;
    private List<Sensor> sensors;
    private List<ProductDTO> products;

    public VolumeDTO() {
        //this.sensors = new ArrayList<>();
        //this.products = new ArrayList<>();
    }

    public VolumeDTO(long id, String state, String creationDate) {
        this.id = id;
        this.state = state;
        this.creationDate = creationDate;
        //this.sensors = new ArrayList<>();
        //this.products = new ArrayList<>();
    }

    // Converts an entity to a DTO Volume class
    public static VolumeDTO from(Volume volume) {
        return new VolumeDTO(
               volume.getId(),
               volume.getState(),
               volume.getCreationDate().toString()
        );
    }

    public static List<VolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }
    public String getState() {return state;}
    public String getCreationDate() {return creationDate;}
    public List<Sensor> getSensors() {
        return sensors;
    }
    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setState(String state) {this.state = state;}
    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }


    public void addProducts(List<ProductDTO> products) {
        if (this.products == null)
            this.products = new LinkedList<>();
        this.products.addAll(products);
    }
    public void removeProduct(Product product) {products.remove(product);}
}
