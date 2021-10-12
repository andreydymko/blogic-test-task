package servlets;

import dao.entities.Company;
import services.interfaces.CompanyService;
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

@Path("/companies")
public class CompanyServlet {
    @EJB
    private CompanyService companyService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompaniesPage(@HeaderParam("Range") RangeHeader range,
                                     @QueryParam("sort") OrdersListParam orders) {
        List<Company> list = companyService.getPage(range.getFrom(),
                range.getLength(),
                orders != null ? orders.getOrderArr() : null);
        GenericEntity<List<Company>> genericEntity = new GenericEntity<List<Company>>(list) {};

        return Response
                .ok(genericEntity)
                .header("Content-Range",
                        ContentRangeHeader.fromLength(range.getUnits(),
                                range.getFrom(),
                                (long) list.size(),
                                range.getLength()))
                .build();
    }

    @GET
    @Path("{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanyById(@PathParam("id") Long id) {
        Company company = companyService.getById(id);
        if (company == null) {
            return Response.noContent().build();
        }
        return Response.ok(company).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewCompany(Company company) {
        try {
            companyService.add(company);
        } catch (EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), e.getMessage()).build();
        }
        Long id = company.getId();
        return Response.created(uriInfo.getAbsolutePathBuilder()
                .path("companies")
                .path(String.valueOf(id))
                .build())
                .build();
    }

    @PUT
    @Path("{id: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCompany(@PathParam("id") Long companyId, Company company) {
        companyService.update(companyId, company);
        return Response.accepted().build();
    }

    @DELETE
    @Path("{id: \\d+}")
    public Response deleteCompanyById(@PathParam("id") Long companyId) {
        companyService.deleteById(companyId);
        return Response.noContent().build();
    }
}

