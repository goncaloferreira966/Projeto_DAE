package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Manager;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerDTO implements Serializable {

    private String username;
    private String password;
    private String name;
    private String email;

    public ManagerDTO() {
    }

    public ManagerDTO(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Converts an entity  to a DTO client class
    public static ManagerDTO from(Manager manager) {
        return new ManagerDTO(
                manager.getUsername(),
                manager.getPassword(),
                manager.getName(),
                manager.getEmail()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<ManagerDTO> from(List<Manager> managers) {
        return managers.stream().map(ManagerDTO::from).collect(Collectors.toList());
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
