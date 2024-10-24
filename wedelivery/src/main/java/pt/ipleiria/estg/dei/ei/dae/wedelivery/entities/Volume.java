package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.*;

@Table(name = "volumes")
@NamedQueries(
        {
                @NamedQuery(
                        name = "getAllVolumes",
                        query = "SELECT v FROM Volume v"
                ),
                @NamedQuery(
                        name = "getVolumeById",
                        query = "SELECT v FROM Volume v WHERE v.id =:id"
                ),
                @NamedQuery(
                        name = "getAllVolumesByProduct",
                        query = "SELECT v FROM Volume v JOIN v.products p WHERE p.id = :id"
                ),
                @NamedQuery(
                        name = "getAllVolumesByOrder",
                        query = "SELECT v FROM Volume v JOIN v.order o WHERE o.code = :code"
                ),
        }
)
@Entity
public class Volume {
    @Id
    private long id;
    private String state;
    @NotNull
    private Date creationDate;
    @Version
    private int version;
    @ManyToMany
    @JoinTable(
            name = "volume_product",
            joinColumns = @JoinColumn(
                    name = "volume_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "id"
            )
    )
    private List<Product> products;

    @OneToMany(mappedBy = "volume")
    private List<Sensor> sensors;

    @ManyToOne
    @NotNull
    private Order order;

    public Volume() {
    }

    public Volume(long id, String state, Date creationDate, Order order) {
        this.id = id;
        this.state = state;
        this.creationDate = creationDate;
        this.order = order;
        this.products = new LinkedList<>();
        this.sensors = new LinkedList<>();
    }

    public long getId() {return id;}
    public String getState() {return state;}
    public Date getCreationDate() {return creationDate;}
    public int getVersion() {return version;}
    public Order getOrder() {return order;}
    public List<Product> getProducts() {
        if (products == null)
            products = new LinkedList<>();
        return products;
    }
    public List<Sensor> getSensors() {
        if (sensors == null)
            sensors = new LinkedList<>();
        return sensors;
    }

    public void setId(long id) {this.id = id;}
    public void setState(String state) {this.state = state;}
    public void setVersion(int version) {this.version = version;}
    public void setOrder(Order order) {this.order = order;}
    public void setProducts(List<Product> products) {
        if (this.products == null)
            this.products = new LinkedList<>();
        this.products = products;
    }
    public void setSensors(List<Sensor> sensors) {
        if (this.sensors == null)
            this.sensors = new LinkedList<>();
        this.sensors = sensors;
    }

    /***************** Volume Methods   ******************/

    /*****************  Products Methods   ******************/
    public void addProduct(Product product) {
        if (!this.products.contains(product)){
            this.products.add(product);
            product.addVolume(this);
        }
    }
    public void removeProduct(Product product) {
        if (!products.contains(product))
            throw new RuntimeException("Product " + product.getId() + " don't exist in volume " + id);
        products.remove(product);
        product.removeVolume(this);
    }

    /*****************  Sensors Methods   ******************/
    public void addSensor(Sensor sensor) {
        if (!this.sensors.contains(sensor)){
            this.sensors.add(sensor);
            sensor.setVolume(this);
        }
    }
    public void removeSensor(Sensor sensor) {
        if (!sensors.contains(sensor))
            throw new RuntimeException("Sensor " + sensor.getId() + " don't exist in volume " + id);
        sensors.remove(sensor);
        sensor.setVolume(null);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Volume volume = (Volume) o;
        return Objects.equals(id, volume.id);
    }
    /*
    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }*/
}
