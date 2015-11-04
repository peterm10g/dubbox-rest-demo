package com.ericsson.csp.tsc.api.resource;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.ericsson.csp.tsc.api.pojo.TscUserProfile;

@Path("user")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ ContentType.APPLICATION_JSON_UTF_8 })
public interface UserResource {
    @POST
    @Path("/")
    Response postUser(TscUserProfile user);
    
    @GET
    @Path("{args}")
    Response queryUser(@PathParam("args")  
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "args should number,printable char.") 
        @Size(min=1, max=17, message="userId length min {min} ,max {max}") String args);
    
    @DELETE
    @Path("{userId}")
    Response delUser(@PathParam("userId") 
        @Size(min=6, max=8, message="userId length min {min} ,max {max}") 
        @Pattern(regexp = "^\\w+$", message = "userId should number,printable char.") String userId);
    
    @PUT
    @Path("{userId}")
    Response putUser(@PathParam("userId") 
        @Size(min=6, max=8, message="userId length min {min} ,max {max}") 
        @Pattern(regexp = "^\\w+$", message = "userId should number,printable char.")String userId, TscUserProfile tscUserProfile);
    
}
