package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.OrderBean;
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

    @GET
    @Path("/client/{username}") // Define o caminho para incluir o ID do cliente
    public List<OrderDTO> getOrdersByClient(@PathParam("username") String username) {
        return OrderDTO.from(orderBean.findOrdersByClientId(username));
    }

    @GET
    @Path("/operator/{username}") // Define o caminho para incluir o ID do cliente
    public List<OrderDTO> getOrdersByOperator(@PathParam("username") String username) {
        return OrderDTO.from(orderBean.findOrdersByOperatorId(username));
    }
}
