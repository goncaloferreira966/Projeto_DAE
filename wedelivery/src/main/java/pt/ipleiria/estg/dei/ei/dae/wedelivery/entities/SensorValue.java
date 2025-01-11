package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class SensorValue {
    @Id
    private long id;
    @NotNull
    private String value;
    @ManyToOne
    @NotNull
    private Sensor sensor;
    @NotNull
    private Date date;
    @Version
    private int version;

    public SensorValue() {
    }

    public SensorValue(long id, String value, Date date, Sensor sensor) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.sensor = sensor;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public int getVersion() {
        return version;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date) {
        this.date = date;
    }
}
