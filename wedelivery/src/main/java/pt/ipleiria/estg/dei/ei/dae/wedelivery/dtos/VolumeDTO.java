package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Volume;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO {
    private long id;
    private List<Sensor> sensors;
    private List<Product> products;

    public VolumeDTO() {
        this.sensors = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public VolumeDTO(long id) {
        this.id = id;
        this.sensors = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    // Converts an entity to a DTO Volume class
    public static VolumeDTO from(Volume volume) {
        return new VolumeDTO(
               // volume.getId()
        );
    }

    public static List<VolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
