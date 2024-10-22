package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Volume;

import java.util.List;

@Path("volumes") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class VolumeService {
    @EJB
    private VolumeBean volumeBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/volumes/”
    public List<VolumeDTO> getAllVolumes() {
        return VolumeDTO.from(volumeBean.findAll());
    }

    @GET
    @Path("{id}")
    public Response getSensorById(@PathParam("id") long id) {
        Volume volume = volumeBean.find(id);
        VolumeDTO volumeDTO = VolumeDTO.from(volume);
        return Response.ok(volumeDTO).build();
    }
}
