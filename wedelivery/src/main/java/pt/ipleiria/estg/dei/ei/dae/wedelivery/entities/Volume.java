package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Volume {
    @Id
    private long id;
    private List<Sensor> sensors;
    private List<Product> products;

    public Volume() {
        this.sensors = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public Volume(long id) {
        this.id = id;
        this.sensors = new ArrayList<>();
        this.products = new ArrayList<>();
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
