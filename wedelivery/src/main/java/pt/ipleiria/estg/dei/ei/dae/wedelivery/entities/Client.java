package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT c FROM Client c ORDER BY c.name" // JPQL
        )
})
public class Client extends User implements Serializable {

    public Client() {
    }

    public Client(String username, String password, String name, String email) {
        super(email, name, password, username);
    }
}
