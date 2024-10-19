package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
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
    private Date purchaseDate;
    private Date deliveryDate;
    @NotNull
    private String state;

    public Order(Date deliveryDate, Date purchaseDate, Client client, long code, String state) {
        this.deliveryDate = deliveryDate;
        this.purchaseDate = purchaseDate;
        this.client = client;
        this.code = code;
        this.state = state;
    }

    public Order() {

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

    public void setState(String state) {
        this.state = state;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
