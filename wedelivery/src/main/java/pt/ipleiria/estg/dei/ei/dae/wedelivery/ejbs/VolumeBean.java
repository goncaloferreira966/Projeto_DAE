package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.EJB;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ProductBean productBean;
    //long id, String state, LocalDate creationDate
    public void create(long id, String state, Date creationDate)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {
        //
        try {
            if (exists(id)) {
                throw new MyEntityExistsException(
                        "volume with id '" + id + "' already exists");
            }

            var volume = new Volume(id, state, creationDate);
            entityManager.persist(volume);

            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
    /*********************  Volume  ***********************************/
    public List<Volume> findAll() {
        // remember, maps to: “SELECT s FROM volume v ORDER BY v.id”
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }
    public Volume find(long id) {
        var volume = entityManager.find(Volume.class, id);
        return volume;
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
