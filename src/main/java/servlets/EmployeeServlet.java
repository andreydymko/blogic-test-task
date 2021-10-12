package servlets;

import dao.entities.Employee;
import services.interfaces.EmployeeService;
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
public class EmployeeServlet {

    @EJB
    private EmployeeService employeeService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("employees/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return Response.noContent().build();
        }
        return Response.ok(employee).build();
    }

    @GET
    @Path("companies/{id: \\d+}/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeesPageByCompanyId(@PathParam("id") Long companyId,
                                                @HeaderParam("Range") RangeHeader range,
                                                @QueryParam("sort") OrdersListParam orders) {
        List<Employee> list = employeeService.getPageByCompanyId(companyId,
                range.getFrom(),
                range.getLength(),
                orders != null ? orders.getOrderArr() : null);
        GenericEntity<List<Employee>> genericEntity = new GenericEntity<List<Employee>>(list) {};

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
    @Path("employees")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEmployee(Employee employee) {
        try {
            employeeService.add(employee);
        } catch (EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), e.getMessage()).build();
        }
        Long id = employee.getId();
        return Response.created(uriInfo.getAbsolutePathBuilder()
                .path("employees")
                .path(String.valueOf(id))
                .build())
                .build();
    }

    @PUT
    @Path("employees/{id: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") Long employeeId, Employee employee) {
        employeeService.update(employeeId, employee);
        return Response.accepted().build();
    }

    @DELETE
    @Path("employees/{id: \\d+}")
    public Response deleteEmployeeById(@PathParam("id") Long employeeId) {
        employeeService.deleteById(employeeId);
        return Response.noContent().build();
    }
}
