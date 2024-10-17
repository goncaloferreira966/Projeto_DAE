package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdministrators",
                query = "SELECT a FROM Administrator a ORDER BY a.name" // JPQL
        )
})
public class Administrator extends User implements Serializable {
    public Administrator(String email, String name, String password, String username) {
        super(email, name, password, username);
    }
    public Administrator() {
    }
}
