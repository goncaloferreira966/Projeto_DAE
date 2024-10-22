package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOperators",
                query = "SELECT a FROM Operator a ORDER BY a.name" // JPQL
        )
})
public class Operator extends User implements Serializable {
    @OneToMany(mappedBy = "operator")
    private List<Order> orders;

    public Operator(String email, String name, String password, String username) {
        super(email, name, password, username);
        orders = new ArrayList<>();
    }
    public Operator() {
        orders = new ArrayList<>();
    }
}
