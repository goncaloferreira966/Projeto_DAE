package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Volume;

import java.util.List;

@Path("volumes") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class VolumeService {
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private SensorBean sensorBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/volumes/”
    public Response getAllVolumes() {
        var volumeDTOs = VolumeDTO.from(volumeBean.findAll());
        return Response.ok(volumeDTOs).build();

    }

    @GET
    @Path("{id}")
    public Response getSensorById(@PathParam("id") long id) {
        Volume volume = volumeBean.find(id);
        VolumeDTO volumeDTO = VolumeDTO.from(volume);
        var products = productBean.findAllProductsByVolumeId(id);
        var productDTOs = ProductDTO.from(products);
        var sensors = sensorBean.findAllSensorsByVolumeId(id);
        var sensorsDTOs = SensorDTO.from(sensors);
        volumeDTO.addProducts(productDTOs);
        volumeDTO.addSensors(sensorsDTOs);
        return Response.ok(volumeDTO).build();
    }
    @GET
    @Path("{id}/details")
    public Response getVolumeDetails(@PathParam("id") long id) {
        Volume volume = volumeBean.find(id);
        VolumeDTO volumeDTO = VolumeDTO.from(volume);
        var products = productBean.findAllProductsByVolumeId(id);
        var productDTOs = ProductDTO.from(products);
        for (var productDTO : productDTOs) {
            var productId = productDTO.getId();
            var supplier = productBean.findWithSupplier(productId);
            var warehouse = productBean.findWithWarehouse(productId);
            productDTO.setSupplier(SupplierDTO.from(supplier));
            productDTO.setWarehouse(WarehouseDTO.from(warehouse));
        }
        var sensors = sensorBean.findAllSensorsByVolumeId(id);
        var sensorsDTOs = SensorDTO.from(sensors);
        volumeDTO.addProducts(productDTOs);
        volumeDTO.addSensors(sensorsDTOs);
        return Response.ok(volumeDTO).build();
    }
}
