package top.crowley.dubbox.rest.demo.provider.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import top.crowley.dubbox.rest.demo.api.exceptions.ServiceException;
import top.crowley.dubbox.rest.demo.api.pojo.QueryRespMessage;
import top.crowley.dubbox.rest.demo.api.pojo.RespMessage;
import top.crowley.dubbox.rest.demo.api.pojo.TscUserProfile;
import top.crowley.dubbox.rest.demo.api.resource.UserResource;
import top.crowley.dubbox.rest.demo.api.util.Constants;
import top.crowley.dubbox.rest.demo.provider.service.UserService;

import com.alibaba.fastjson.JSON;

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
        RespMessage message = new RespMessage();
        QueryRespMessage<TscUserProfile> queryMessage = new QueryRespMessage<TscUserProfile>();
        try {
            queryMessage = tscUserService.query(args);
        } catch (Exception e) {
            LOGGER.error("query user service unavailable: {}", e);
            message.setAll(Status.SERVICE_UNAVAILABLE.getStatusCode(), "Service Unavailable");
            queryMessage.setList(new ArrayList<TscUserProfile>());
            queryMessage.setRespMessage(message);
            return Response.status(Status.SERVICE_UNAVAILABLE).entity("Service Unavailable").build();
        }
        LOGGER.debug("return list:{}", JSON.toJSONString(queryMessage.getList()));
        return Response.status(Status.OK).entity(queryMessage.getList()).build();
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
