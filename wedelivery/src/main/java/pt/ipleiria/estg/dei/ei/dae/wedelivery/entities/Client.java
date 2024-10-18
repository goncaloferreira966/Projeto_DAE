package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT c FROM Client c ORDER BY c.name" // JPQL
        )
})
public class Client extends User implements Serializable {
    @NotNull
    private int nif;
    @NotNull
    private String postalCode;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @NotNull
    private String address;
    @OneToMany(mappedBy = "client")
    private List<Order> orders;


    public Client() {
        this.orders = new ArrayList<>();
    }

    public Client(String email, String name, String password, String username, int nif, String postalCode, String city, String country, String address) {
        super(email, name, password, username);
        this.nif = nif;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.address = address;
        this.orders = new ArrayList<>();
    }

    public int getNif() {
        return nif;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }
}
