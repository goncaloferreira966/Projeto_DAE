package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.mail.MessagingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.security.Authenticated;

import java.util.*;

@Path("orders") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Manager", "Operator"})
public class OrderService {
    @Context
    private SecurityContext securityContext;
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
    @EJB
    private ClientBean clientBean;
    @EJB
    private EmailBean emailBean;
    @EJB
    private WarehouseBean warehouseBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    public List<OrderDTO> getAllOrders() {
        return OrderDTO.from(orderBean.findAll());
    }

    @GET
    @Path("{code}")
    @RolesAllowed({"Client"})//Acesso apenas a este método
    public Response getOder(@PathParam("code") long code) {
        //Vai procurar a order
        var order = orderBean.find(code);
        //Procurar o username do user owner da order
        var username = order.getClient().getUsername();

        //Verifica se o utilizador pode ter acesso ou nao
        var principal = securityContext.getUserPrincipal();

        //Verifica se esse user deve ou nao ver essa order(verifica se é owner)
        if(securityContext.isUserInRole("Client") && !principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }


        var orderDTO = OrderDTO.from(order);
        var volumes = volumeBean.findVolumesByOrderId(code);

        var volumeDTOs = VolumeDTO.from(volumes);

        orderDTO.setVolumes(volumeDTOs);
        return Response.ok(orderDTO).build();
    }

    @GET
    @Path("{code}/volume/{id}")
    @RolesAllowed({"Client"})//Acesso apenas a este método
    public VolumeDTO getOrderVolumeById(@PathParam("code") long code, @PathParam("id") long id) {
        var volume = volumeBean.find(id);

        //Procurar o username do user owner do volume
        var username = volume.getOrder().getClient().getUsername();

        //Verifica se o utilizador pode ter acesso ou nao
        var principal = securityContext.getUserPrincipal();

        //Verifica se esse user deve ou nao ver essa order(verifica se é owner)
        if(securityContext.isUserInRole("Client") && !principal.getName().equals(username)) {
            return new VolumeDTO();
        }

        var volumeDTO = VolumeDTO.from(volume);

        // Buscar e adicionar sensores
        var sensors = sensorBean.findAllSensorsByVolumeId(id);
        var sensorDTOs = SensorDTO.from(sensors);
        volumeDTO.addSensors(sensorDTOs);

        // Buscar e adicionar produtos
        var products = productBean.findAllProductsByVolumeId(id);
        var productDTOs = ProductDTO.from(products);
        volumeDTO.setProducts(productDTOs);

        // Retornar o VolumeDTO
        return volumeDTO;
    }


    @POST
    @Path("/")
    @RolesAllowed({"Client"})
    public Response createNewOrder (OrderRequestDTO orderRequestDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MessagingException {

        List<Product> products = new LinkedList<>();
        for (ProductDTO productDTO : orderRequestDTO.getProductsDTOs()){

            products.add(productBean.findById(productDTO.getId()));
        }
        OrderDTO orderDTO = orderBean.makeNewOrder( products);

        emailBean.send(orderBean.find(orderDTO.getCode()).getClient().getEmail(), "Order " + orderDTO.getCode() + " created", "Order " + orderDTO.getCode() + " created successfully.");

            return Response.status(Response.Status.CREATED)
                    .entity(orderDTO.getVolumes())
                    .build();
        }

    @PATCH
    @Path("{code}")
    @RolesAllowed({"Client"})
    public Response updateState(@PathParam("code") long code, OrderDTO orderDTO) {
        Order order = orderBean.find(code);

        //Verifica se o utilizador pode ter acesso ou nao
        var principal = securityContext.getUserPrincipal();

        //Verifica se esse user deve ou nao ver essa order(verifica se é owner)
        if(securityContext.isUserInRole("Client") && !principal.getName().equals(order.getClient().getUsername())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        // Verifica se o order existe
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }

        order.setState(orderDTO.getState());

        // Persiste a alteração
        orderBean.update(order);

        OrderDTO orderUpdated = OrderDTO.from(order);
        return Response.ok(orderUpdated).build();
    }
}
