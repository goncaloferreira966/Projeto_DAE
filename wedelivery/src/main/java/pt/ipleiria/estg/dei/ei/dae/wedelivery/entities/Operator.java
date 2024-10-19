package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOperators",
                query = "SELECT a FROM Operator a ORDER BY a.name" // JPQL
        )
})
public class Operator extends User implements Serializable {
    public Operator(String email, String name, String password, String username) {
        super(email, name, password, username);
    }
    public Operator() {
    }
}
