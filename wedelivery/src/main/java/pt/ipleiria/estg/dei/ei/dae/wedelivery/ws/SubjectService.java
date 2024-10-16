package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.SubjectDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.SubjectBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Subject;

import java.util.List;

@Path("subject") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SubjectService {
    @EJB
    private SubjectBean subjectBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/subject/”
    public List<SubjectDTO> getAllSubjects() {
        return SubjectDTO.from(subjectBean.findAll());
    }

    @POST
    @Path("/")
    public Response createNewSubject (SubjectDTO subjectDTO) {
        subjectBean.create(
                subjectDTO.getCode(),
                subjectDTO.getName(),
                subjectDTO.getSchoolarYear(),
                subjectDTO.getCourseYear(),
                subjectDTO.getCourseCode()
        );
        Subject newSubject = subjectBean.find(subjectDTO.getCode());
        return Response.status(Response.Status.CREATED)
                .entity(SubjectDTO.from(newSubject))
                .build();
    }
}
