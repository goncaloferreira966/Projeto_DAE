package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Teacher;

@Stateless
public class TeacherBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String email, String name, String password, String username, String office) {
        var teacher = new Teacher(email, name, password, username, office);
        entityManager.persist(teacher);
    }
}
