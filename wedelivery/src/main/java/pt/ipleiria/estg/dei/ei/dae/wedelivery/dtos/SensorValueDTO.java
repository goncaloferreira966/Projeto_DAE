package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.SensorValue;

import java.util.Date;

public class SensorValueDTO {
    private long id;
    private String value;
    private Date date;

    public SensorValueDTO() {
    }

    public SensorValueDTO(long id, String value, Date date) {
        this.id = id;
        this.value = value;
        this.date = date;
    }

    // Converts an entity to a DTO Sensor class
    public static SensorValueDTO from(SensorValue sensorValue) {
        return new SensorValueDTO(
                sensorValue.getId(),
                sensorValue.getValue(),
                sensorValue.getDate()
        );
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
