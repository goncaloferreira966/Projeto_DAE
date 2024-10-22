package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ManagerDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.ManagerBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Manager;

import java.util.List;

@Path("managers") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ManagerService {
    @EJB
    private ManagerBean managerBean;
    @EJB
    private OrderBean orderBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    public List<ManagerDTO> getAllManagers() {
        return ManagerDTO.from(managerBean.findAll());
    }

    @GET
    @Path("{username}")
    public Response getClient(@PathParam("username") String username) {
        var manager = managerBean.find(username);
        return Response.ok(ManagerDTO.from(manager)).build();
    }

    @POST
    @Path("/")
    public Response createNewManager (ManagerDTO managerDTO) {
        managerBean.create(
                managerDTO.getEmail(),
                managerDTO.getName(),
                managerDTO.getPassword(),
                managerDTO.getUsername()
        );
        Manager newManager = managerBean.find(managerDTO.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(ManagerDTO.from(newManager))
                .build();
    }

    @GET
    @Path("{username}/orders") // Define o caminho para incluir o ID do cliente
    public List<OrderDTO> getOrdersByOperator(@PathParam("username") String username) {
        return OrderDTO.from(orderBean.findOrdersByOperatorId(username));
    }
}
