package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Table(name = "sensors")
@NamedQueries(
        {
                @NamedQuery(
                        name = "getAllSensors",
                        query = "SELECT s FROM Sensor s"
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
    private int currentValue;
    //int[] history = new int[5];
    @NotNull
    private boolean busy;
    @NotNull
    private boolean expedition;
    @ManyToOne
    private Volume volume;
    @Version
    private int version;


    public Sensor() {
    }

    public Sensor(long id, String type, int currentValue, boolean busy, boolean expedition) {
        this.id = id;
        this.type = type;
        this.currentValue = currentValue;
        this.busy = busy;
        this.expedition =expedition;
    }

    public long getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public int getCurrentValue() {
        return currentValue;
    }
    public boolean isBusy() {
        return busy;
    }
    public boolean isExpedition() {
        return expedition;
    }
    public Volume getVolume() {
        if (volume == null)
            throw new RuntimeException("Sensor " + id + " doesn't have a volume");
        return volume;}

    public void setId(long id) {
        this.id = id;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setCurrentValue(int currentValue) {
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
