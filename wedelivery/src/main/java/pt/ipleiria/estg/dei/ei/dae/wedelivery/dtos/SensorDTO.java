package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;

import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO {
    private long id;
    private String type;
    private String currentValue;
    boolean busy;
    boolean expedition;
    private VolumeDTO volume;
    private List<SensorValueDTO> sensorValues;

    public SensorDTO() {
    }

    public SensorDTO(long id, String type, String currentValue, boolean busy, boolean expedition) {
        this.id = id;
        this.type = type;
        this.currentValue = currentValue;
        this.busy = busy;
        this.expedition =expedition;

    }

    // Converts an entity to a DTO Sensor class
    public static SensorDTO from(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getType(),
                sensor.getCurrentValue(),
                sensor.isBusy(),
                sensor.isExpedition()
        );
    }

    public static List<SensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
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
    public VolumeDTO getVolume() {return volume;}
    public List<SensorValueDTO> getSensorValues() {
        return sensorValues;
    }

    public void setSensorValues(List<SensorValueDTO> sensorValues) {
        this.sensorValues = sensorValues;
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
    public void setVolume(VolumeDTO volume) {this.volume = volume;}
}
