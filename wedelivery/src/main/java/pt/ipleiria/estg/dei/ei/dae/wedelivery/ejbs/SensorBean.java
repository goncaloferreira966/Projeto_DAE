package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Restriction;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, String type, int currentValue, boolean busy, boolean expedition)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {
        try {
            if (exists(id)) {
                throw new MyEntityExistsException(
                        "sensor with id '" + id + "' already exists");
            }

            var sensor = new Sensor(id, type, currentValue, busy, expedition);
            entityManager.persist(sensor);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Sensor> findAll() {
        // remember, maps to: “SELECT s FROM sensor s ORDER BY s.id”
        return entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    public Sensor find(long id) {
        var sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new RuntimeException("sensor " + id + " not found");
        }
        return sensor;
    }

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(s.id) FROM Sensor s WHERE s.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

}
