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
    private int current_value;
    //int[] history = new int[5];
    private boolean busy;
    private boolean expedition;

    public Sensor() {
    }

    public Sensor(long id, String type, int current_value, boolean busy, boolean expedition) {
        this.id = id;
        this.type = type;
        this.current_value = current_value;
        this.busy = busy;
        this.expedition =expedition;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getCurrent_value() {
        return current_value;
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

    public void setCurrent_value(int current_value) {
        this.current_value = current_value;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setExpedition(boolean expedition) {
        this.expedition = expedition;
    }
}
