package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.SensorValue;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class SensorValueBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private SensorBean sensorBean;

    public void create(long id, String value, Date date, long idSensor)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {
        try {
            if (exists(id)) {
                throw new MyEntityExistsException(
                        "sensorValue with id '" + id + "' already exists");
            }
            var sensor = sensorBean.find(idSensor);
            var sensorValue = new SensorValue(id, value, date, sensor);
            entityManager.persist(sensorValue);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<SensorValue> findAllSensorValuesById(long id) {
        return entityManager.createNamedQuery("getAllSensorsValuesById", SensorValue.class)
                .setParameter("id", id)
                .getResultList();
    }

    /******************************************************************/
    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(sv.id) FROM SensorValue sv WHERE sv.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }
}
