package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Restriction;

import java.util.List;

@Stateless
public class RestrictionBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, String type, int maxValue, int minValue) {
        var restriction = new Restriction(id, type, maxValue, minValue);
        entityManager.persist(restriction);
    }

    public List<Restriction> findAll() {
        // remember, maps to: “SELECT s FROM restriction r ORDER BY r.id”
        return entityManager.createNamedQuery("getAllRestrictions", Restriction.class).getResultList();
    }

    public Restriction find(long id) {
        var restriction = entityManager.find(Restriction.class, id);
        if (restriction == null) {
            throw new RuntimeException("restriction " + id + " not found");
        }
        return restriction;
    }
}
