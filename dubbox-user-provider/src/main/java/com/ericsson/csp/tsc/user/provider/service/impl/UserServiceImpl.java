package com.ericsson.csp.tsc.user.provider.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.ericsson.csp.tsc.api.exceptions.ServiceException;
import com.ericsson.csp.tsc.api.pojo.RespMessage;
import com.ericsson.csp.tsc.api.pojo.TscUserProfile;
import com.ericsson.csp.tsc.api.util.Constants;
import com.ericsson.csp.tsc.user.provider.dao.TscUserProfileDao;
import com.ericsson.csp.tsc.user.provider.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired TscUserProfileDao userProfileDao;
    
    @Override
    public RespMessage save(TscUserProfile userProfile) throws Exception {
        RespMessage message = new RespMessage();
        //userId exist.
        String userId = userProfile.getUserId();
        if(queryByUserId(userProfile.getUserId()) != null) {
            LOGGER.error("UserId:{}, {}", userId, Constants.ErrorDesc.USER_EXISTS_CODE);
            message.setAll(Constants.ErrorCode.USER_EXISTS_CODE, Constants.ErrorDesc.USER_EXISTS_CODE);
            throw new ServiceException(Status.BAD_REQUEST.getStatusCode(), message);
        }
        LOGGER.debug("save user info:{}", JSON.toJSONString(userProfile));
        userId = userProfileDao.save(userProfile);
        if(StringUtils.isBlank(userId)) {
            LOGGER.error("save user but not return userId.");
            message.setAll(Status.SERVICE_UNAVAILABLE.getStatusCode(), "save user but not return userId.");
            throw new ServiceException(Status.SERVICE_UNAVAILABLE.getStatusCode(), message);
        } else {
            message.setMessage(Constants.SUCCESS_DESC);
            LOGGER.info("add user success.userId: {}", userId);
        }
        
        return message;
    }
    
    @Override
    public TscUserProfile queryByUserId(String userId) {
        if(StringUtils.isBlank(userId)) {
            LOGGER.error("userId is blank");
            return null;
        }
        TscUserProfile user = userProfileDao.queryById(userId);
        return user;
    }
    
    @Override
    public List<TscUserProfile> query(String arg) throws Exception{
        List<TscUserProfile> list = null;
        LOGGER.debug("arg length = {}", arg.length());
        //must vin
        if(arg.length() > 8) {
            list = userProfileDao.queryByVin(arg);
        } else {//maybe userId or vin
            TscUserProfile user = userProfileDao.queryById(arg);
            if(user == null) {
                list = userProfileDao.queryByVin(arg);
            } else {
                list = new ArrayList<TscUserProfile>();
                list.add(user);
            }
        }
        LOGGER.debug("query users:{}", JSON.toJSONString(list));
        
        return list;
    }

    @Override
    public RespMessage del(String userId) throws Exception{
        RespMessage message = new RespMessage();
        //judge userId blank
        if(StringUtils.isBlank(userId)) {
            LOGGER.error("userId is blank.");
            message.setAll(Constants.ErrorCode.USER_INVALID_PARAM_CODE, Constants.ErrorDesc.USER_INVALID_PARAM_CODE + "userId is blank.");
            throw new ServiceException(Status.BAD_REQUEST.getStatusCode(), message);
        }
        LOGGER.debug("del userId {}", userId);
        Integer code = userProfileDao.del(userId);
        //judge code
        if(code == null || code <= 0) {
            LOGGER.error("del user affect {} rows.", code);
            message.setAll(Constants.ErrorCode.USER_NOTEXISTS_CODE, new StringBuilder(Constants.ErrorDesc.USER_NOTEXISTS_CODE).append(", userId:").append(userId).toString());
            throw new ServiceException(Status.NOT_FOUND.getStatusCode(), message);
        } else {
            message.setMessage(Constants.SUCCESS_DESC);
            LOGGER.info("del user {} success.", userId);
        }
        
        return message;
    }

    @Override
    public RespMessage update(TscUserProfile userProfile) throws Exception{
        RespMessage message = new RespMessage();
        if(queryByUserId(userProfile.getUserId()) == null) {
            message.setStatusCode(Constants.ErrorCode.USER_NOTEXISTS_CODE);
            message.setMessage(Constants.ErrorDesc.USER_NOTEXISTS_CODE);
            throw new ServiceException(Status.NOT_FOUND.getStatusCode(), message);
        }
        //If there is a persistent instance with the same identifier, an exception is thrown
        userProfileDao.update(userProfile);
        message.setMessage(Constants.SUCCESS_DESC);
        LOGGER.info("update user success.userId: {}", userProfile.getUserId());
        
        return message;
    }

}
