package servlets;

import dao.entities.Department;
import services.interfaces.DepartmentService;
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
public class DepartmentServlet {
    @EJB
    private DepartmentService departmentService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("departments/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartmentById(@PathParam("id") Long id) {
        Department department = departmentService.getById(id);
        if (department == null) {
            return Response.noContent().build();
        }
        return Response.ok(department).build();
    }

    @GET
    @Path("companies/{id: \\d+}/departments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartmentsPageByCompanyId(@PathParam("id") Long companyId,
                                                  @HeaderParam("Range") RangeHeader range,
                                                  @QueryParam("sort") OrdersListParam orders) {
        List<Department> list = departmentService.getPageByCompanyId(companyId,
                range.getFrom(),
                range.getLength(),
                orders != null ? orders.getOrderArr() : null);
        GenericEntity<List<Department>> genericEntity = new GenericEntity<List<Department>>(list) {};

        return Response
                .ok(genericEntity)
                .header("Content-Range",
                        ContentRangeHeader.fromLength(range.getUnits(),
                                range.getFrom(),
                                (long) list.size(),
                                range.getLength()))
                .build();
    }

    @POST
    @Path("departments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDepartment(Department department) {
        try {
            departmentService.add(department);
        } catch (EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), e.getMessage()).build();
        }
        Long id = department.getId();
        return Response.created(uriInfo.getAbsolutePathBuilder()
                .path("departments")
                .path(String.valueOf(id))
                .build())
                .build();
    }

    @PUT
    @Path("departments/{id: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDepartment(@PathParam("id") Long departmentId, Department department) {
        departmentService.update(departmentId, department);
        return Response.accepted().build();
    }

    @DELETE
    @Path("departments/{id: \\d+}")
    public Response deleteDepartmentById(@PathParam("id") Long departmentId) {
        departmentService.deleteById(departmentId);
        return Response.noContent().build();
    }
}
