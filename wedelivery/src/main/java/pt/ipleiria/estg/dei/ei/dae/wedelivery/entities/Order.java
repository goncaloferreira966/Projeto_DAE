package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o" // JPQL
        ),
        @NamedQuery(
                name = "getAllOrdersByClient",
                query = "SELECT o FROM Order o WHERE o.client.username = :username"
        ),
        @NamedQuery(
            name = "getAllOrdersByOperator",
            query = "SELECT o FROM Order o WHERE o.operator.username = :username"
        ),
        @NamedQuery(
                name = "getAllOrdersByState",
                query = "SELECT o FROM Order o WHERE o.state = :state"
        ),
        @NamedQuery(
                name = "getAllOrdersByVolume",
                query = "SELECT o FROM Order o JOIN o.volumes v WHERE v.id = :id"
        )
})
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    private long code;
    @ManyToOne
    @JoinTable(
            name = "order_client",
            joinColumns = @JoinColumn(
                    name = "order_code",
                    referencedColumnName = "code"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "client_username",
                    referencedColumnName = "username"
            )
    )
    private Client client;
    @ManyToOne
    @JoinTable(
            name = "order_operator",
            joinColumns = @JoinColumn(
                    name = "order_code",
                    referencedColumnName = "code"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "operator_username",
                    referencedColumnName = "username"
            )
    )
    private Operator operator;
    private Date purchaseDate;
    private Date deliveryDate;
    @NotNull
    private String state;
    @Version
    private int version;

    @OneToMany(mappedBy = "order")
    private List<Volume> volumes;

    public Order(Date deliveryDate, Date purchaseDate, Client client, Operator operator , long code, String state) {
        this.deliveryDate = deliveryDate;
        this.purchaseDate = purchaseDate;
        this.client = client;
        this.code = code;
        this.state = state;
        this.operator = operator;
    }

    public Order() {

    }

    public Operator getOperator() {
        return operator;
    }
    public long getCode() {
        return code;
    }
    public Client getClient() {
        return client;
    }
    public String getState() {
        return state;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public Date getDeliveryDate() {
        return deliveryDate;
    }
    public List<Volume> getVolumes() {
        if (volumes == null)
            throw new RuntimeException("Order " + code + " don't have volumes");
        return volumes;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    public void setCode(long code) {
        this.code = code;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public void setVolumes(List<Volume> volumes) {
        if (this.volumes == null)
            this.volumes = new LinkedList<>();
        this.volumes = volumes;
    }


    /******************* Volume Methods   ******************/
    public void addVolume(Volume volume) {
        if (!this.volumes.contains(volume)){
            this.volumes.add(volume);
            volume.setOrder(this);
        }
    }
    public void removeVolume(Volume volume) {
        if (!volumes.contains(volume))
            throw new RuntimeException("Volume " + volume.getId() + " don't exist in order " + code);
        volumes.remove(volume);
        volume.setOrder(null);
    }
}
