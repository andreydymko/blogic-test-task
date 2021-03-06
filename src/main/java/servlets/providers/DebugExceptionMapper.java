package servlets.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DebugExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        e.printStackTrace();
        return Response.serverError().entity(e.getMessage()).build();
    }
}
