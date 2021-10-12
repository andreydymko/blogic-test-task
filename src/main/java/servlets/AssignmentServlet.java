package servlets;

import com.sun.javafx.binding.StringFormatter;
import dao.entities.Assignment;
import services.interfaces.AssignmentService;
import servlets.providers.helpers.types.ContentRangeHeader;
import servlets.providers.helpers.types.OrdersListParam;
import servlets.providers.helpers.types.RangeHeader;

import javax.ejb.EJB;
import javax.persistence.EntityExistsException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("")
public class AssignmentServlet {
    @EJB
    private AssignmentService assignmentService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("assignments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAssignmentsPage(@HeaderParam("Range") RangeHeader range,
                                       @QueryParam("sort") OrdersListParam orders) {
        List<Assignment> assignments = assignmentService.getPage(range.getFrom(),
                range.getLength(),
                orders != null ? orders.getOrderArr() : null);

        GenericEntity<List<Assignment>> genericEntity = new GenericEntity<List<Assignment>>(assignments) {};

        return Response
                .ok(genericEntity)
                .header("Content-Range",
                        ContentRangeHeader.fromLength(range.getUnits(),
                                range.getFrom(),
                                (long) assignments.size(),
                                range.getLength()))
                .build();
    }

    @GET
    @Path("assignments/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAssigmentById(@PathParam("id") Long id) {
        Assignment assignment = assignmentService.getById(id);
        if (assignment == null) {
            return Response.noContent().build();
        }
        return Response.ok(assignment).build();
    }

    @GET
    @Path("employees/{id: \\d+}/assignments/my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPageOfMyAssignments(@PathParam("id") Long myId,
                                           @HeaderParam("Range") RangeHeader range,
                                           @QueryParam("sort") OrdersListParam orders) {
        List<Assignment> assignments = assignmentService.getPageOfMyAssignments(myId,
                range.getFrom(),
                range.getLength(),
                orders != null ? orders.getOrderArr() : null);
        GenericEntity<List<Assignment>> genericEntity = new GenericEntity<List<Assignment>>(assignments) {};

        return Response
                .ok(genericEntity)
                .header("Content-Range",
                        ContentRangeHeader.fromLength(range.getUnits(),
                                range.getFrom(),
                                (long) assignments.size(),
                                range.getLength()))
                .build();
    }

    @GET
    @Path("employees/{id: \\d+}/assignments/for_me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPageOfAssignmentsForMe(@PathParam("id") Long myId,
                                              @HeaderParam("Range") RangeHeader range,
                                              @QueryParam("sort") OrdersListParam orders) {
        List<Assignment> assignments =  assignmentService.getPageOfAssignmentsForMe(myId,
                range.getFrom(),
                range.getLength(),
                orders != null ? orders.getOrderArr() : null);
        GenericEntity<List<Assignment>> genericEntity = new GenericEntity<List<Assignment>>(assignments) {};

        return Response
                .ok(genericEntity)
                .header("Content-Range",
                        ContentRangeHeader.fromLength(range.getUnits(),
                                range.getFrom(),
                                (long) assignments.size(),
                                range.getLength()))
                .build();
    }

    @POST
    @Path("assignments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAssignment(Assignment assignment) {
        try {
            assignmentService.add(assignment);
        } catch (EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), e.getMessage()).build();
        }
        Long id = assignment.getId();
        return Response.created(uriInfo.getAbsolutePathBuilder()
                .path("assignments")
                .path(String.valueOf(id))
                .build())
                .build();
    }

    @POST
    @Path("assignments/state/{id: \\d+}/{state: [a-z]+}")
    public Response changeAssignmentState(@PathParam("id") Long id, @PathParam("state") String newState) {
        Assignment assignment;

        switch (newState) {
            case "execution":
                assignment = assignmentService.sendOnExecution(id);
                break;
            case "control":
                assignment = assignmentService.sendOnControl(id);
                break;
            case "revision":
                assignment = assignmentService.sendOnRevision(id);
                break;
            case "acceptation":
                assignment = assignmentService.acceptAssignment(id);
                break;
            default:
                return Response.status(422,
                        StringFormatter.format("State '{0}' is not supported.", newState).getValue()).build();
        }

        return Response.ok(assignment).build();
    }

    @PUT
    @Path("assignments/{id: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAssignment(@PathParam("id") Long assignmentId, Assignment assignment) {
        assignmentService.update(assignmentId, assignment);
        return Response.accepted().build();
    }

    @DELETE
    @Path("assignments/{id: \\d+}")
    public Response deleteAssignmentById(@PathParam("id") Long assignmentId) {
        assignmentService.deleteById(assignmentId);
        return Response.noContent().build();
    }
}
