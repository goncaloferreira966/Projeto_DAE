package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.hibernate.procedure.ProcedureOutputs;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.SensorValueBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.security.Authenticated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("sensors") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed({"Manager", "Operator"})
public class SensorService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private SensorValueBean sensorValueBean;
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/sensors/”
    public List<SensorDTO> getAllSensors() {
        List<SensorDTO> sensors =  SensorDTO.from(sensorBean.findAll());

        for (SensorDTO sensor : sensors) {
            var sensorValues = sensorValueBean.findAllSensorValuesById(sensor.getId());

            List<SensorValueDTO> sensorValuesDTOs = new ArrayList<>();

            for (SensorValue sensorValue : sensorValues) {
                sensorValuesDTOs.add(SensorValueDTO.from(sensorValue));
            }

            sensor.setSensorValues(sensorValuesDTOs);
        }

        return sensors;
    }

    @GET
    @Path("{id}")
    public Response getSensorById(@PathParam("id") long id) {
        Sensor sensor = sensorBean.find(id);
        SensorDTO sensorDTO = SensorDTO.from(sensor);

        var sensorValues = sensorValueBean.findAllSensorValuesById(id);

        List<SensorValueDTO> sensorValuesDTOs = new ArrayList<>();

        for (SensorValue sensorValue : sensorValues) {
            sensorValuesDTOs.add(SensorValueDTO.from(sensorValue));
        }

        sensorDTO.setSensorValues(sensorValuesDTOs);

        return Response.ok(sensorDTO).build();
    }

    @GET
    @Path("{id}/details")
    public Response getSensorByIdDetails(@PathParam("id") long id) {
        var sensor = sensorBean.findWithVolume(id);
        var sensorDTO = SensorDTO.from(sensor);
        sensorDTO.setVolume(VolumeDTO.from(sensor.getVolume()));

        var sensorValues = sensorValueBean.findAllSensorValuesById(id);

        List<SensorValueDTO> sensorValuesDTOs = new ArrayList<>();

        for (SensorValue sensorValue : sensorValues) {
            sensorValuesDTOs.add(SensorValueDTO.from(sensorValue));
        }

        sensorDTO.setSensorValues(sensorValuesDTOs);

        return Response.ok(sensorDTO).build();
    }

    @PATCH
    @Path("{id}")
    public Response updateCurrentValue(@PathParam("id") long id, SensorDTO sensorDTO) throws MyConstraintViolationException {
        Sensor sensor = sensorBean.find(id);

        // Verifica se o sensor existe
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Sensor not found").build();
        }

        sensor.setCurrentValue(sensorDTO.getCurrentValue());

        // Persiste a alteração
        sensorBean.update(sensor);

        //Cria um novo sensorValue
        sensorValueBean.create(Math.abs(UUID.randomUUID().hashCode()), sensorDTO.getCurrentValue(), new Date(), id);

        SensorDTO sensorDTOUpdated = SensorDTO.from(sensor);

        //Verificar se estragou algum produto
        Volume volume = sensor.getVolume();

        if(!volume.getOrder().getState().equals("Delivered")){
            int counter = 0;
            for(Product product : volume.getProducts() ) {
                for(Restriction restriction : product.getRestrictions() ) {
                    if(sensor.getType().equals(restriction.getType())) {
                        if(!sensor.getType().equals("GPS")){
                            if((Double.parseDouble(sensorDTO.getCurrentValue()) < restriction.getMinValue()) ||  Double.parseDouble(sensorDTO.getCurrentValue()) > restriction.getMaxValue()){
                                counter ++;
                            }
                        }
                    }
                }
            }

            //Alterar o estado de um volume sem deixar voltar para um estado inferior em termos de gravidade
            switch (counter) {
                case 0:
                    if ("No damage".equals(volume.getState())) {
                        volume.setState("No damage");
                    }
                    break;
                case 1:
                    if ("No damage".equals(volume.getState()) || "Minor damage".equals(volume.getState())) {
                        volume.setState("Minor damage");
                    }
                    break;
                case 2:
                    if ("No damage".equals(volume.getState()) || "Minor damage".equals(volume.getState()) || "Moderate damage".equals(volume.getState())) {
                        volume.setState("Moderate damage");
                    }
                    break;
                case 3:
                    if ("No damage".equals(volume.getState()) ||
                            "Minor damage".equals(volume.getState()) ||
                            "Moderate damage".equals(volume.getState()) ||
                            "Significant damage".equals(volume.getState())) {
                        volume.setState("Significant damage");
                    }
                    break;
                case 4:
                    if ("No damage".equals(volume.getState()) ||
                            "Minor damage".equals(volume.getState()) ||
                            "Moderate damage".equals(volume.getState()) ||
                            "Significant damage".equals(volume.getState()) ||
                            "Heavily damaged".equals(volume.getState())) {
                        volume.setState("Heavily damaged");
                    }
                    break;
                default:
                    if (!"Unusable".equals(volume.getState())) { // Qualquer estado pode se tornar "Unusable"
                        volume.setState("Unusable");
                    }
                    break;
            }
        }

        return Response.ok(sensorDTOUpdated).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        Sensor sensor = sensorBean.find(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Sensor não encontrado").build();
        }
        if(sensorBean.delete(sensor)){
            return Response.ok("Sensor com ID " + id + " foi removido com sucesso.").build();
        }
        else{
            return Response.ok("Sensor com ID " + id + " foi removido sem sucesso.").build();
        }
    }
}
