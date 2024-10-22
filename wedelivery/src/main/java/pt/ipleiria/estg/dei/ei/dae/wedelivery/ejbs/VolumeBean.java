package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {
        try {
            if (exists(id)) {
                throw new MyEntityExistsException(
                        "volume with id '" + id + "' already exists");
            }

            var volume = new Volume(id);
            entityManager.persist(volume);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Volume> findAll() {
        // remember, maps to: “SELECT s FROM volume v ORDER BY v.id”
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }

    public Volume find(long id) {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("volume " + id + " not found");
        }
        return volume;
    }

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(v.id) FROM Volume v WHERE v.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }
}
