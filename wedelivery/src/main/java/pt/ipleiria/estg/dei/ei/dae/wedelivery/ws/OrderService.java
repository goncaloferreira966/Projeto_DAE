package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("orders") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class OrderService {
    @EJB
    private OrderBean orderBean;
    @EJB
    private OperatorBean operatorBean;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ProductBean productBean;



    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    public List<OrderDTO> getAllOrders() {
        return OrderDTO.from(orderBean.findAll());
    }

    @GET
    @Path("{code}")
    public Response getOder(@PathParam("code") long code) {
        var order = orderBean.find(code);
        var orderDTO = OrderDTO.from(order);
        var volumes = volumeBean.findVolumesByOrderId(code);
        var volumeDTOs = VolumeDTO.from(volumes);
        orderDTO.setVolumes(volumeDTOs);
        return Response.ok(orderDTO).build();
    }

    @GET
    @Path("{code}/volume/{id}")
    public Response getOrderVolumeById(@PathParam("code") long code, @PathParam("id") long id) {
        var order = orderBean.find(code);
        var orderDTO = OrderDTO.from(order);
        var volume = volumeBean.find(id);

        var volumeDTO = VolumeDTO.from(volume);
        if (volume.getOrder().getCode() != code) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var sensors = sensorBean.findAllSensorsByVolumeId(id);
        var sensorDTOs = SensorDTO.from(sensors);
        volumeDTO.addSensors(sensorDTOs);
        var products = productBean.findAllProductsByVolumeId(id);
        var productDTOs = ProductDTO.from(products);
        volumeDTO.addProducts(productDTOs);

        orderDTO.addVolume(volumeDTO);
        return Response.ok(volumeDTO).build();
    }


    @POST
    @Path("/")
    public Response createNewOrder (OrderDTO orderDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        orderBean.create(
                orderDTO.getCode(),
                orderDTO.getPurchaseDate(),
                orderDTO.getDeliveryDate(),
                orderDTO.getUsername(),
                orderDTO.getUsernameOperator(),
                orderDTO.getState()
        );
        Order newOrder = orderBean.find(orderDTO.getCode());
        return Response.status(Response.Status.CREATED)
                .entity(OrderDTO.from(newOrder))
                .build();
    }
}
