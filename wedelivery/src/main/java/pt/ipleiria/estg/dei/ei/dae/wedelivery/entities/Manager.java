package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Version;

import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllManagers",
                query = "SELECT m FROM Manager m ORDER BY m.name" // JPQL
        )
})
public class Manager extends User implements Serializable {

    public Manager(String email, String name, String password, String username) {
        super(email, name, password, username);
    }

    public Manager() {
    }
}
