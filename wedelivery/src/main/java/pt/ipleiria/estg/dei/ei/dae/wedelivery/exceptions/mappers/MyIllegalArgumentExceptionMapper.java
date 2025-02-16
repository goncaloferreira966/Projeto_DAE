package pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.mappers;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.logging.Logger;

public class MyIllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    private static final Logger logger = Logger.getLogger(IllegalArgumentException.class.getCanonicalName());

    @Override
    public Response toResponse(IllegalArgumentException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMsg)
                .build();
    }
}
