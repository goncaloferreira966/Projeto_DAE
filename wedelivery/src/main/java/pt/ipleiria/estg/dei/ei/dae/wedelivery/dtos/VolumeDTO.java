package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Volume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO {
    private long id;
    private String state;
    private String creationDate;
    private List<SensorDTO> sensors;
    private List<ProductDTO> products;
    private OrderDTO orderDTO;
    public VolumeDTO() {
        //this.sensors = new ArrayList<>();
        //this.products = new ArrayList<>();
    }

    public VolumeDTO(long id, String state, String creationDate) {
        this.id = id;
        this.state = state;
        this.creationDate = creationDate;
        //this.sensors = new ArrayList<>();
        //this.products = new ArrayList<>();
    }
    public void VolumeDTO(long id, String state, String creationDate) {
        this.id = id;
        this.state = state;
        this.creationDate = creationDate;
        //this.sensors = new ArrayList<>();
        //this.products = new ArrayList<>();
    }

    // Converts an entity to a DTO Volume class
    public static VolumeDTO from(Volume volume) {
        return new VolumeDTO(
               volume.getId(),
               volume.getState(),
               volume.getCreationDate().toString()
        );
    }


    public static List<VolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }
    public String getState() {return state;}
    public String getCreationDate() {return creationDate;}
    public List<SensorDTO> getSensors() {
        return sensors;
    }
    public List<ProductDTO> getProducts() {
        return products;
    }
    public OrderDTO getOrderDTO(){return orderDTO;}

    public void setId(long id) {
        this.id = id;
    }
    public void setState(String state) {this.state = state;}
    public void setSensors(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }
    public void setProducts(List<ProductDTO> products) {this.products = products;}
    public void setOrderDTO(OrderDTO orderDTO){
        if (this.orderDTO == null){
            this.orderDTO = new OrderDTO();
        }
        this.orderDTO = orderDTO;
    }

    /****************** Products -> Volume *****************************/
    public void addProducts(List<ProductDTO> products) {
        if (this.products == null)
            this.products = new LinkedList<>();
        this.products.addAll(products);
    }
    public void removeProduct(Product product) {products.remove(product);}

    /****************** Sensors -> Volume *****************************/
    public void addSensors(List<SensorDTO> sensors) {
        if (this.sensors == null)
            this.sensors = new LinkedList<>();
        this.sensors.addAll(sensors);
    }
    public void removeSensor(SensorDTO sensor) {sensors.remove(sensor);}



}
