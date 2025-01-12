package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Warehouse;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private RestrictionBean restrictionBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private SensorBean sensorBean;
    //long id, String state, LocalDate creationDate, Order order
    public void create(long id, String state, Date creationDate, long idOrder)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {
        //
        try {
            if (exists(id)) {
                throw new MyEntityExistsException(
                        "volume with id '" + id + "' already exists");
            }
            var order = orderBean.find(idOrder);
            var volume = new Volume(id, state, creationDate, order);
            entityManager.persist(volume);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void update(Volume volume) {
        entityManager.merge(volume);  // Atualiza o volume na bd
    }
    /*********************  Volume  ***********************************/
    public List<Volume> findAll() {
        // remember, maps to: “SELECT s FROM volume v ORDER BY v.id”
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }

    public Volume find(long id) {
        return entityManager.find(Volume.class, id);
    }

    /**************** Volumes <---> Product ***************************/
    public List<Volume> findVolumesByProductId(long id) {
        return entityManager.createNamedQuery("getAllVolumesByProduct", Volume.class)
                .setParameter("id", id)
                .getResultList();
    }
    public List<Volume> findVolumesByIds(List<Long> ids) {
        return entityManager.createQuery(
                "SELECT v FROM Volume v WHERE v.id IN :ids",
                Volume.class
        ).setParameter("ids", ids).getResultList();
    }
    public List<Volume> findVolumesByDate(String date) {
        var data = date.split("/");
        return entityManager.createQuery(
                "SELECT v FROM Volume v WHERE v.creationDate = :date",
                Volume.class
        ).setParameter("date", LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])).toString()).getResultList();
    }
    //It's the same think with the productBean,
    // It's redundant and unnecessary to have this method here because
    // it's the same as the one in the productBean
    public void addProductToVolume(long idVolume, long idProduct) {
        var volume = find(idVolume);
        var product = entityManager.find(Product.class, idProduct);
        volume.addProduct(product);
    }

    /*****************  Volume -> Sensor  ***********************************/
    public void addSensorToVolume(long idVolume, long idSensor) {
        var volume = find(idVolume);
        var sensor = sensorBean.find(idSensor);
        volume.addSensor(sensor);
    }
    public void removeSensorFromVolume(long idVolume, long idSensor) {
        var volume = find(idVolume);
        var sensor = sensorBean.find(idSensor);
        volume.removeSensor(sensor);
    }

    /*****************  Volume -> Order  ***********************************/
    public List<Volume> findVolumesByOrderId(long id) {
        return entityManager.createNamedQuery("getAllVolumesByOrder", Volume.class)
                .setParameter("code", id)
                .getResultList();
    }


    /*****************  Volume -> Sensonr  ***********************************/
    public void setSensoresToVolume(long idVolume) {
        var volume = this.find(idVolume);
        for (Product product : volume.getProducts()) {

            var restrictionsByProduct = restrictionBean.findRestrictionsByProductId(product.getId());
            for (var restriction : restrictionsByProduct) {
                var type = restriction.getType();
                boolean sensorExists = volume.getSensors().stream()
                        .anyMatch(sensor -> sensor.getType().equals(type));

                if (!sensorExists) {
                    long newSensorID = getNewID();
                    try {
                        sensorBean.create(newSensorID, type, "0", true, false);
                        addSensorToVolume(idVolume, newSensorID);
                    } catch (MyEntityNotFoundException | MyEntityExistsException | MyConstraintViolationException e) {
                        // Log the error and handle it appropriately
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private long getNewID(){
        return Math.abs(UUID.randomUUID().hashCode());
    }
    /******************************************************************/
    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(v.id) FROM Volume v WHERE v.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }
}
