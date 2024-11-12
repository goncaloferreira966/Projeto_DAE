package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.annotation.security.RolesAllowed;
import jakarta.mail.MessagingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.ClientBean;
import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.security.Authenticated;
import java.util.List;

@Path("clients") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed({"Manager", "Operator"})
public class ClientService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private ClientBean clientBean;
    @EJB
    private OrderBean orderBean;
    @EJB
    private EmailBean emailBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    public List<ClientDTO> getAllClients() {
        return ClientDTO.from(clientBean.findAll());
    }

    @GET
    @Path("{username}")
    @RolesAllowed({"Client"})//Acesso apenas a este método
    public Response getClient(@PathParam("username") String username) {
        //Verifica se o utilizador pode ter acesso ou nao
        var principal = securityContext.getUserPrincipal();

        if(securityContext.isUserInRole("Client") && !principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        var client = clientBean.findWithOrders(username);
        var clientDTO = ClientDTO.from(client);
        clientDTO.setOrders(OrderDTO.from(client.getOrders()));
        return Response.ok(clientDTO).build();
    }


    @POST
    @Path("/")
    public Response createNewClient (ClientDTO clientDTO)
        throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
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
    @RolesAllowed({"Client"})
    public Response getOrdersByClient(@PathParam("username") String username) {
        //Verifica se o utilizador pode ter acesso ou nao
        var principal = securityContext.getUserPrincipal();
        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(OrderDTO.from(orderBean.findOrdersByClientId(username))).build();
    }

    @POST
    @Path("/{username}/email")
    public Response sendEmail(@PathParam("username") String username, EmailDTO email)
            throws MyEntityNotFoundException, MessagingException {
        Client client = clientBean.find(username);
        emailBean.send(client.getEmail(), email.getSubject(), email.getBody());
        return Response.status(Response.Status.OK).entity("E-mail sent").build();
    }
}
