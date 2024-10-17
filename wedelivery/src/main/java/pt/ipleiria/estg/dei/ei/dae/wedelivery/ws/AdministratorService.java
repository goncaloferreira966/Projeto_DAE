package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.AdministratorDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.AdministratorBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Administrator;
import java.util.List;

@Path("administrators") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministratorService {
    @EJB
    private AdministratorBean administratorBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    public List<AdministratorDTO> getAllAdministrators() {
        return AdministratorDTO.from(administratorBean.findAll());
    }

    @GET
    @Path("{username}")
    public Response getAdministrator(@PathParam("username") String username) {
        var admin = administratorBean.find(username);
        return Response.ok(AdministratorDTO.from(admin)).build();
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (AdministratorDTO administratorDTO) {
        administratorBean.create(
                administratorDTO.getUsername(),
                administratorDTO.getPassword(),
                administratorDTO.getName(),
                administratorDTO.getEmail()
        );
        Administrator newAdministrator = administratorBean.find(administratorDTO.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(AdministratorDTO.from(newAdministrator))
                .build();
    }
}
