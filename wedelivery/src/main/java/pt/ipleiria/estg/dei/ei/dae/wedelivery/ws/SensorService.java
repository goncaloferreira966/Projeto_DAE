package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Sensor;

import java.util.List;

@Path("sensors") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/sensors/”
    public List<SensorDTO> getAllSensors() {
        return SensorDTO.from(sensorBean.findAll());
    }

    @GET
    @Path("{id}")
    public Response getSensorById(@PathParam("id") long id) {
        Sensor sensor = sensorBean.find(id);
        SensorDTO sensorDTO = SensorDTO.from(sensor);
        return Response.ok(sensorDTO).build();
    }

    @GET
    @Path("{id}/details")
    public Response getSensorByIdDetails(@PathParam("id") long id) {
        var sensor = sensorBean.findWithVolume(id);
        var sensorDTO = SensorDTO.from(sensor);
        sensorDTO.setVolume(VolumeDTO.from(sensor.getVolume()));
        return Response.ok(sensorDTO).build();
    }

    @PATCH
    @Path("{id}/updateCurrentValue")
    public Response updateCurrentValue(@PathParam("id") long id, SensorDTO sensorDTO) {
        Sensor sensor = sensorBean.find(id);

        // Verifica se o sensor existe
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Sensor not found").build();
        }

        sensor.setCurrentValue(sensorDTO.getCurrentValue());

        // Persiste a alteração
        sensorBean.update(sensor);

        SensorDTO sensorDTOUpdated = SensorDTO.from(sensor);
        return Response.ok(sensorDTOUpdated).build();
    }
}
