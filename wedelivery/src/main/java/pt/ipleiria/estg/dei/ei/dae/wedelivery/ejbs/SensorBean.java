package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
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

    @EJB
    private VolumeBean volumeBean;
    //long id, String type, int currentValue, boolean busy, boolean expedition
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

    public void update(Sensor sensor) {
        entityManager.merge(sensor);  // Atualiza o sensor na bd
    }

    public void delete(Sensor sensor) {
        if(sensor.getVolume() == null){
            entityManager.remove(sensor);
            entityManager.flush(); // Garantir que a operação seja aplicada imediatamente
        }
    }

    /*****************  Sensor -> Volume  ***********************************/
    public List<Sensor> findAllSensorsByVolumeId(long id) {
        return entityManager.createNamedQuery("getAllSensorsByVolume", Sensor.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Sensor findWithVolume(long id){
        var sensor = this.find(id);
        Hibernate.initialize(sensor.getVolume());
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
