package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Table(name = "sensors")
@NamedQueries(
        {
                @NamedQuery(
                        name = "getAllSensors",
                        query = "SELECT s FROM Sensor s order by s.id"
                ),
                @NamedQuery(
                        name = "getAllSensorsByVolume",
                        query = "SELECT s FROM Sensor s JOIN s.volume v WHERE v.id = :id"
                ),
        }
)
@Entity
public class Sensor {
    @Id
    private long id;
    @NotNull
    private String type;
    private String currentValue;
    //int[] history = new int[5];
    @NotNull
    private boolean busy;
    @NotNull
    private boolean expedition;
    @ManyToOne
    private Volume volume;
    @OneToMany(mappedBy = "sensor")
    private List<SensorValue> history;
    @Version
    private int version;


    public Sensor() {
        this.history = new ArrayList<>();
    }

    public Sensor(long id, String type, String currentValue, boolean busy, boolean expedition) {
        this.id = id;
        this.type = type;
        this.currentValue = currentValue;
        this.busy = busy;
        this.expedition =expedition;
        this.history = new ArrayList<>();
    }

    public long getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public String getCurrentValue() {
        return currentValue;
    }
    public boolean isBusy() {
        return busy;
    }
    public boolean isExpedition() {
        return expedition;
    }
    public Volume getVolume() {
        return volume;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    public void setExpedition(boolean expedition) {
        this.expedition = expedition;
    }
    public void setVolume(Volume volume) {
        if (volume == null)
            this.volume = new Volume();
        this.volume = volume;
    }
    public List<SensorValue> getHistory() {
        return history;
    }

    /****************** Sensor -> Volume ***********************/
    /*public void addVolume(Volume volume) {
        if (this.volume != volume) {
            this.volume = volume;
            volume.addSensor(this);
        }
    }
    public void removeVolume(Volume volume) {
        if (this.volume != volume)
            throw new RuntimeException("Sensor " + id + " don't have volume " + volume.getId());
        this.volume = null;
        volume.removeSensor(this);
    }*/
}
