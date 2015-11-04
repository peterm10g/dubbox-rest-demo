package com.ericsson.csp.tsc.user.provider.resource;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.ericsson.csp.tsc.api.exceptions.ServiceException;
import com.ericsson.csp.tsc.api.pojo.RespMessage;
import com.ericsson.csp.tsc.api.pojo.TscUserProfile;
import com.ericsson.csp.tsc.api.resource.UserResource;
import com.ericsson.csp.tsc.api.util.Constants;
import com.ericsson.csp.tsc.user.provider.service.UserService;

public class UserResourceImpl implements UserResource {

    private Logger LOGGER = LoggerFactory.getLogger(UserResourceImpl.class);
    @Autowired UserService tscUserService;
    
    
    @Override
    public Response postUser(TscUserProfile user) {
        RespMessage message = null;
        try {
            message = tscUserService.save(user);
        } catch(ServiceException e) {
            LOGGER.error("postUser error: {}", e.toString());
            return Response.status(e.getErrorCode()).entity(e.toString()).build();
            
        } catch (Exception e) {
            LOGGER.error("postUser service unavailable: {}", e);
            return Response.status(Status.SERVICE_UNAVAILABLE).entity("Service Unavailable").build();
        }
        
        return Response.status(Status.CREATED).entity(message.toString()).build();
    }

    @Override
    public Response queryUser(String args) {
        List<TscUserProfile> list = null;
        try {
            list = tscUserService.query(args);
        } catch (Exception e) {
            LOGGER.error("query user service unavailable: {}", e);
            return Response.status(Status.SERVICE_UNAVAILABLE).entity("Service Unavailable").build();
        }
        LOGGER.debug("return list:{}", JSON.toJSONString(list));
        return Response.status(Status.OK).entity(list).build();
    }

    @Override
    public Response delUser(String userId) {
        RespMessage message = null;
        try {
            message = tscUserService.del(userId);
        } catch(ServiceException e) {
            LOGGER.error("postUser error: {}", e.toString());
            return Response.status(e.getErrorCode()).entity(e.toString()).build();
            
        } catch (Exception e) {
            LOGGER.error("postUser service unavailable: {}", e);
            return Response.status(Status.SERVICE_UNAVAILABLE).entity("Service Unavailable").build();
        }
        
        return Response.status(Status.OK).entity(message.toString()).build();
    }

    @Override
    public Response putUser(String userId, TscUserProfile tscUserProfile) {
        RespMessage message = null;
        try {
            message = tscUserService.update(tscUserProfile);
        } catch(ServiceException e) {
            LOGGER.error("putUser error: {}", e.toString());
            return Response.status(e.getErrorCode()).entity(e.toString()).build();
            
        } catch(HibernateException e) {
            LOGGER.warn("putUser error: {}", e.toString());
            message = new RespMessage(Constants.ErrorCode.USER_NOTEXISTS_CODE, Constants.ErrorDesc.USER_NOTEXISTS_CODE);
            return Response.status(Status.NOT_FOUND).entity(message.toString()).build();
            
        } catch (Exception e) {
            LOGGER.error("postUser service unavailable: {}", e);
            return Response.status(Status.SERVICE_UNAVAILABLE).entity("Service Unavailable").build();
        }
        
        return Response.status(Status.OK).entity(message.toString()).build();
    }
}
