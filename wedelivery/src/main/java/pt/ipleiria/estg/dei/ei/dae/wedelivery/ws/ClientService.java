package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.ClientBean;
import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
import java.util.List;

@Path("clients") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ClientService {
    @EJB
    private ClientBean clientBean;
    @EJB
    private OrderBean orderBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    public List<ClientDTO> getAllClients() {
        return ClientDTO.from(clientBean.findAll());
    }

    @GET
    @Path("{username}")
    public Response getClient(@PathParam("username") String username) {
        var client = clientBean.find(username);
        return Response.ok(ClientDTO.from(client)).build();
    }

    @POST
    @Path("/")
    public Response createNewClient (ClientDTO clientDTO){
        clientBean.create(
                clientDTO.getUsername(),
                clientDTO.getPassword(),
                clientDTO.getName(),
                clientDTO.getEmail(),
                clientDTO.getNif(),
                clientDTO.getPostalCode(),
                clientDTO.getCountry(),
                clientDTO.getCity(),
                clientDTO.getAddress()
        );
        Client newClient = clientBean.find(clientDTO.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(ClientDTO.from(newClient))
                .build();
    }

    @GET
    @Path("{username}/orders") // Define o caminho para incluir o ID do cliente
    public List<OrderDTO> getOrdersByClient(@PathParam("username") String username) {
        return OrderDTO.from(orderBean.findOrdersByClientId(username));
    }
}
