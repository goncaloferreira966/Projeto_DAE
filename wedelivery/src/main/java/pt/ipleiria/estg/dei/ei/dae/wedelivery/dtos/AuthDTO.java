package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

public class AuthDTO implements Serializable {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public AuthDTO(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public AuthDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}