package presentation.rest.customer;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import facade.exceptions.ApplicationException;
import facade.handlers.IAddCustomerHandler;
import facade.handlers.IAppTokens;
import facade.interfaces.ICustomer;
import presentation.rest.CollectionMeta;
import presentation.rest.security.RolesAllowed;

@Path(Customers.HREF)
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Stateless
public class Customers {

    public static final String REL = "customers";
    public static final String HREF = "v1/customers";

    @EJB private IAppTokens tokens;
    @EJB private IAddCustomerHandler addCustomerHandler;
    @Context private UriInfo uriInfo;    

    @POST
    @RolesAllowed("ADMIN")
    public Response addCustomer(@Valid CustomerData customerData) throws ApplicationException, URISyntaxException {
    	try {
    		ICustomer customer = addCustomerHandler.addCustomer(customerData.getVAT(), 
    				customerData.getDenomination(), customerData.getPhoneNumber(), 
    				customerData.discountType());
    		String href = uriInfo.getBaseUri().toString() + HREF + "/" + customer.getId();
    		return Response.created(new URI(href)).build();
    	} catch (ApplicationException e) {
    		return Response.status(Status.PRECONDITION_FAILED).build();
    	}
    }

    @GET
    @RolesAllowed({"ADMIN", "BASIC"})
    public Response getCustomers() throws ApplicationException {
        CustomerList res = new CustomerList();
        for (ICustomer customer : addCustomerHandler.getCustomers()) {
    		String href = uriInfo.getBaseUri().toString() + HREF + "/" + customer.getId();
    		res.addUser(new CustomerGet(href, customer));
        }
        res.setMeta(new CollectionMeta(res.size(), null, null, 1, res.size()));
        return Response.ok(res).build();
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"ADMIN", "BASIC"})
    public Response getCustomer(@PathParam("id") int id) throws ApplicationException {
    	try {
    		ICustomer c = addCustomerHandler.getCustomer(id);
    		String href = uriInfo.getBaseUri().toString() + HREF + "/" + c.getId();
    		return Response.ok(new CustomerGet(href, c)).build();
    	} catch (ApplicationException e) {
    		throw new NotFoundException(e);
    	}
    }
    
    @DELETE
    @Path("{id}")
    @RolesAllowed("BASIC")
    public Response deleteCustomer(@PathParam("id") int id) throws ApplicationException {
    	try {
    		addCustomerHandler.deleteCustomer(id);
    		return Response.ok().build();
    	} catch (ApplicationException e) {
    		throw new NotFoundException(e);
    	}
    }
    
}
