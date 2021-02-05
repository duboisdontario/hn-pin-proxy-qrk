package ca.ontario.moh.hsc.prsb.reg.hnpinproxy;

import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//import ca.ontario.moh.hsc.prsb.reg.hnpinproxy.ProxyResponse;
import ca.ontario.moh.hsc.prsb.reg.hnpinproxy.data.ProxyRepository;
import ca.ontario.moh.hsc.prsb.reg.hnpinproxy.entity.Proxy;

/*
* HealthNumberPINResource.java
*
* Outward facing REST web service for looking up HN proxy PIN.
*/

@Path("/hnpinproxy")
public class HealthNumberPINResource {

    @Inject
    ProxyRepository data;

    //TODO remove this
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String healthcheck() {
        return "Success";
    }

    @GET
    @Path("pin/{pin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByPIN(@HeaderParam("EXT_AUDIT_ID") String extAuditId, @PathParam("pin") String pin){

        //set vars
        String audit_id = generateAuditId();

        ProxyResponse resp = new ProxyResponse();
        resp.auditId = audit_id;
        resp.externalAuditId = extAuditId;
        resp.pin = pin; 
        resp.responseCode = 200;
        resp.responseCodeDescription = "Success";

        //lookup HN by PIN
        Proxy proxy = data.findByPIN(pin);

        if(proxy != null){
            //pin found     
            resp.healthNumber = proxy.healthNumber; //1078870621;
        }else{
            //pin not found            
            resp.responseCodeDescription += " - PIN not found.";            
        }

        return Response.status(Response.Status.OK).entity(resp).build();
    }     

    
    @GET
    @Path("hn/{hn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByHN(@HeaderParam("EXT_AUDIT_ID") String extAuditId, @PathParam("hn") long hn){

        ProxyResponse resp = new ProxyResponse();
        resp.responseCode = 200;
        resp.responseCodeDescription = "Success";

        //set vars
        String audit_id = generateAuditId();

        resp.auditId = audit_id;
        resp.externalAuditId = extAuditId;
        resp.healthNumber = hn;  

        //lookup HN by PIN
        Proxy proxy = data.findByHealthNumber(hn);

        if(proxy != null){
            //pin found     
            resp.pin = proxy.pin;
        }else{
            //pin not found            
            resp.responseCodeDescription += " - hn not found.";            
        }

        return Response.status(Response.Status.OK).entity(resp).build();
    }    

    private String generateAuditId(){
        return UUID.randomUUID().toString();
    }
}