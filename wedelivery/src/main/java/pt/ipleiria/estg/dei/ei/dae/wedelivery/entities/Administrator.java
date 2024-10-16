package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.Entity;
import java.io.Serializable;

@Entity
public class Administrator extends User implements Serializable {
    public Administrator(String email, String name, String password, String username) {
        super(email, name, password, username);
    }
    public Administrator() {
    }
}
