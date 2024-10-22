package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Restriction;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class RestrictionBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, String type, int maxValue, int minValue)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {

        try {
            var restriction = new Restriction(id, type, maxValue, minValue);
            entityManager.persist(restriction);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
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
