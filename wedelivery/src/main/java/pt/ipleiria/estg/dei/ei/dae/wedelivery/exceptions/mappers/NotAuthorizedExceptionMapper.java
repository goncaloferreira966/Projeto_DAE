package pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.mappers;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

    @Override
    public Response toResponse(NotAuthorizedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Not Authorized: " + exception.getMessage())
                .build();
    }
}
