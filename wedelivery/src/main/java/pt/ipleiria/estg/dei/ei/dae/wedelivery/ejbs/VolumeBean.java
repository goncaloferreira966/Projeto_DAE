package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
}
