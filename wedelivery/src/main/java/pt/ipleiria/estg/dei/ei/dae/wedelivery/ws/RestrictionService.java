package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.RestrictionDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.RestrictionBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Restriction;

import java.util.List;

@Path("restrictions") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@RolesAllowed({"Manager", "Operator"})
public class RestrictionService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private RestrictionBean restrictionBean;
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/restrictions/”
    public List<RestrictionDTO> getAllRestrictions() {
        return RestrictionDTO.from(restrictionBean.findAll());
    }

    @GET
    @Path("{id}")
    public Response getRestrictionById(@PathParam("id") long id) {
        Restriction restriction = restrictionBean.find(id);
        RestrictionDTO restrictionDTO = RestrictionDTO.from(restriction);
        return Response.ok(restrictionDTO).build();
    }

    @GET
    @Path("{id}/details")
    public Response getRestrictionByIdDetails(@PathParam("id") long id) {
        var restriction = restrictionBean.findWithProducts(id);
        var restrictionDTO = RestrictionDTO.from(restriction);
        restrictionDTO.setProducts(ProductDTO.from(restriction.getProducts()));
        return Response.ok(restrictionDTO).build();
    }
}
