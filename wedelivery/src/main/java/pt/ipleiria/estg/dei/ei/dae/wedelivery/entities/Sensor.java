package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Table(name = "sensors")

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
}
