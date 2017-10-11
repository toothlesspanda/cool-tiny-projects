package presentation.rest.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import presentation.rest.Link;
import presentation.rest.customer.Customers;

@Path("v1")
public class API {

    @Context private UriInfo uriInfo;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public APIData getAPIData(){
        List<Link> topLevelResources = new ArrayList<>();
        String path = uriInfo.getBaseUri().toString();
       topLevelResources.add(new Link(Customers.REL, path + Customers.HREF));
        return new APIData( 
                new ProductInfo("Sale Sys", "(c) VVS",
                        new Version(1,0,0,0)),
                        topLevelResources);
    }
}
