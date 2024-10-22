package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OperatorDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Order;

import java.util.List;

@Path("orders") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class OrderService {
    @EJB
    private OrderBean orderBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    public List<OrderDTO> getAllOrders() {
        return OrderDTO.from(orderBean.findAll());
    }

    @GET
    @Path("{code}")
    public Response getOder(@PathParam("code") long code) {
        var order = orderBean.find(code);
        return Response.ok(OrderDTO.from(order)).build();
    }


    @POST
    @Path("/")
    public Response createNewOrder (OrderDTO orderDTO) {
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
