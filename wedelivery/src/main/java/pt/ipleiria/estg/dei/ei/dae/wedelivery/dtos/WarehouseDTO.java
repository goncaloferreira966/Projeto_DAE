package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Warehouse;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class WarehouseDTO implements Serializable {
    private String name;
    private String address;
    private String city;
    private String postalCode;

    public WarehouseDTO() {
    }
    public WarehouseDTO(String name, String address, String city, String postalCode) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getName() {return name;}
    public String getAddress() {return address;}
    public String getCity() {return city;}
    public String getPostalCode() {return postalCode;}


    public static WarehouseDTO from(Warehouse warehouse) {
        return new WarehouseDTO(
                warehouse.getName(),
                warehouse.getAddress(),
                warehouse.getCity(),
                warehouse.getPostalCode()
        );
    }
    public static List<WarehouseDTO> from(List<Warehouse> warehouses) {
        return warehouses.stream().map(WarehouseDTO::from).collect(Collectors.toList());
    }


}
