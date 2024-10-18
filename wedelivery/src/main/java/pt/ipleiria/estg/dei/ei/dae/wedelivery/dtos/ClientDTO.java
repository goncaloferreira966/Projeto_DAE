package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO implements Serializable {

    private String username;
    private String password;
    private String name;
    private String email;
    private int nif;
    private String postalCode;
    private String city;
    private String country;
    private String address;

    public ClientDTO() {
    }

    public ClientDTO(String username, String password, String name, String email, int nif, String postalCode, String city, String country, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.address = address;
    }

    // Converts an entity  to a DTO client class
    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getUsername(),
                client.getPassword(),
                client.getName(),
                client.getEmail(),
                client.getNif(),
                client.getPostalCode(),
                client.getCity(),
                client.getCountry(),
                client.getAddress()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<ClientDTO> from(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
