package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OperatorDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.OperatorBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.security.Authenticated;

import java.util.List;

@Path("operators") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed({"Manager", "Operator"})
public class OperatorService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private OperatorBean administratorBean;
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    public List<OperatorDTO> getAllOperators() {
        return OperatorDTO.from(administratorBean.findAll());
    }

    @GET
    @Path("{username}")
    public Response getOperator(@PathParam("username") String username) {
        var admin = administratorBean.find(username);
        return Response.ok(OperatorDTO.from(admin)).build();
    }

    @POST
    @Path("/")
    public Response createNewOperator (OperatorDTO operatorDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        administratorBean.create(
                operatorDTO.getEmail(),
                operatorDTO.getName(),
                operatorDTO.getPassword(),
                operatorDTO.getUsername()
        );
        Operator newOperator = administratorBean.find(operatorDTO.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(OperatorDTO.from(newOperator))
                .build();
    }
}
