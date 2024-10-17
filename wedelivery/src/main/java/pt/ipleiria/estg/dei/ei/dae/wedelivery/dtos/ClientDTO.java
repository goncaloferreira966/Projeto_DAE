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

    public ClientDTO() {
    }

    public ClientDTO(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Converts an entity  to a DTO client class
    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getUsername(),
                client.getPassword(),
                client.getName(),
                client.getEmail()
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


}
