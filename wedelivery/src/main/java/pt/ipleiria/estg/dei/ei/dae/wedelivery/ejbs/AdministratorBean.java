package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Administrator;

@Stateless
public class AdministratorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String email, String name, String password, String username) {
        var administrator = new Administrator(email, name, password, username);
        entityManager.persist(administrator);
    }
}
