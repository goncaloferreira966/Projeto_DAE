package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
                )
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
    //private List<Sensor> sensors;
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

    public Volume() {
    }

    public Volume(long id, String state, Date creationDate) {
        this.id = id;
        this.state = state;
        this.creationDate = creationDate;
        this.products = new LinkedList<>();
    }

    public long getId() {return id;}
    public String getState() {return state;}
    public Date getCreationDate() {return creationDate;}
    public int getVersion() {return version;}
    public List<Product> getProducts() {
        if (products == null)
            products = new LinkedList<>();
        return products;
    }

    public void setId(long id) {this.id = id;}
    public void setState(String state) {this.state = state;}
    public void setVersion(int version) {this.version = version;}
    public void setProducts(List<Product> products) {
        if (this.products == null)
            this.products = new LinkedList<>();
        this.products = products;
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
