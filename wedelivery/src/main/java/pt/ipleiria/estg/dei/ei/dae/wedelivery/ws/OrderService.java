package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Volume;
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
    @RolesAllowed({"Client"})
    public Response createNewOrder (OrderRequestDTO orderRequestDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MessagingException {
        long newOrderID = Math.abs(UUID.randomUUID().hashCode());
        orderBean.create(newOrderID, new Date(), new Date(), "Goncalo", "DinisRX", "Pending");
        var order = orderBean.find(newOrderID);
        long OrderID = order.getCode();

        for (ProductDTO productDTO : orderRequestDTO.getProductsDTOs()) {
            var haveVolume = false;
            if (volumeBean.findVolumesByOrderId(newOrderID).isEmpty()) {
                var newVolumeID = Math.abs(UUID.randomUUID().hashCode());
                volumeBean.create(newVolumeID, "Pending", new Date(), newOrderID);
                productBean.addProductInVolume(productDTO.getId(), newVolumeID, productDTO.getQuantity());
                haveVolume = true;

            } else {
                var volumes = volumeBean.findVolumesByOrderId(newOrderID);
                for (Volume volume : volumes) {
                    var products = productBean.findAllProductsByVolumeId(volume.getId());
                    if (products.stream().noneMatch(product -> product.getId() == productDTO.getId()) && products.stream().allMatch(product -> product.getWarehouse().getName().equals(productBean.findById(productDTO.getId()).getWarehouse().getName()))) {
                        productBean.addProductInVolume(productDTO.getId(), volume.getId(), productDTO.getQuantity());
                        haveVolume = true;
                    }
                }
            }
            if (!haveVolume) {
                var newVolumeID = Math.abs(UUID.randomUUID().hashCode());
                volumeBean.create(newVolumeID, "Pending", new Date(), newOrderID);
                productBean.addProductInVolume(productDTO.getId(), newVolumeID, productDTO.getQuantity());
            }

             /*for (Volume volume : volumeBean.findVolumesByOrderId(newOrderID)) {
                   var products = productBean.findAllProductsByVolumeId(volume.getId());
                   if (products.stream().noneMatch(product -> product.getWarehouse().getName().equals(productBean.findById(productDTO.getId()).getWarehouse().getName()))) {
                       productBean.addProductInVolume(productDTO.getId(), volume.getId(), productDTO.getQuantity());

                   } else {
                       newVolumeID = Math.abs(UUID.randomUUID().hashCode());
                       volumeBean.create(newVolumeID, "Pending", new Date(), newOrderID);
                       productBean.addProductInVolume(productDTO.getId(), newVolumeID, productDTO.getQuantity());
                   }
               }
           }*/
        }


            var orderDTO = OrderDTO.from(order);
            var volume = volumeBean.findVolumesByOrderId(newOrderID);
            List<VolumeDTO> volumeDTOs = VolumeDTO.from(volume);
            for (VolumeDTO volumeDTO : volumeDTOs) {
                volumeDTO.setProducts(ProductDTO.from(productBean.findAllProductsByVolumeId(volumeDTO.getId())));
                orderDTO.addVolume(volumeDTO);

            }

            emailBean.send(orderBean.find(newOrderID).getClient().getEmail(), "Order " + newOrderID + " created", "Order " + newOrderID + " created successfully.");

            return Response.status(Response.Status.CREATED)
                    .entity(volumeDTOs)
                    .build();
        }

    @PATCH
    @Path("{code}")
    public Response updateState(@PathParam("code") long code, OrderDTO orderDTO) {
        Order order = orderBean.find(code);

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
