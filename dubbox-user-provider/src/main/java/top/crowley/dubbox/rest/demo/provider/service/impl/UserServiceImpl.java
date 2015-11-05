package top.crowley.dubbox.rest.demo.provider.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.crowley.dubbox.rest.demo.api.pojo.QueryRespMessage;
import top.crowley.dubbox.rest.demo.api.pojo.RespMessage;
import top.crowley.dubbox.rest.demo.api.pojo.TscUserProfile;
import top.crowley.dubbox.rest.demo.api.util.Constants;
import top.crowley.dubbox.rest.demo.api.util.InvalidationUtil;
import top.crowley.dubbox.rest.demo.provider.dao.UserProfileDao;
import top.crowley.dubbox.rest.demo.provider.service.UserService;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired UserProfileDao userProfileDao;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    @Override
    public RespMessage save(TscUserProfile userProfile) {
        RespMessage message = new RespMessage();
        InvalidationUtil<TscUserProfile> iu = new InvalidationUtil<TscUserProfile>();
        //object is null?
        if(userProfile == null) {
            LOGGER.error("userProfile is null");
            message.setAll(Constants.ErrorCode.USER_INVALID_PARAM_CODE, Constants.ErrorDesc.USER_INVALID_PARAM_CODE + "userProfile is null");
            return message;
        }
        //parameter validation
        Set<ConstraintViolation<TscUserProfile>> constraintViolations = validator.validate(userProfile);
        int size = constraintViolations.size();
        if(size > 0) {
            String msg = iu.formatInvalidationInfo(constraintViolations);
            LOGGER.error("TscUserProfile data validation failed message:{}", msg);
            message.setAll(Constants.ErrorCode.USER_INVALID_PARAM_CODE, Constants.ErrorDesc.USER_INVALID_PARAM_CODE + msg);
            return message;
        }
        String userId = userProfile.getUserId();
        if(queryByUserId(userId) != null) {
            LOGGER.warn("TscUserProfile exist:{}", userId);
            message.setAll(Constants.ErrorCode.USER_EXISTS_CODE, Constants.ErrorDesc.USER_EXISTS_CODE + userId);
            return message;
        }
        
        LOGGER.debug("save user info:{}", JSON.toJSONString(userProfile));
        userId = userProfileDao.save(userProfile);
        if(StringUtils.isBlank(userId)) {
            LOGGER.error("save user but not return userId.");
            message.setAll(Status.SERVICE_UNAVAILABLE.getStatusCode(), "save user but not return userId.");
            return message;
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
    public QueryRespMessage<TscUserProfile> query(String arg) {
        RespMessage message = new RespMessage();
        QueryRespMessage<TscUserProfile> queryMessage = new QueryRespMessage<TscUserProfile>();
        //validation param
        if(StringUtils.isNotBlank(arg) && arg.matches("^\\w+$") && arg.length() < 18) {
            List<TscUserProfile> list = null;
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
            message.setAll(Constants.SUCCESS_CODE, Constants.SUCCESS_DESC);
            queryMessage.setList(list);
            queryMessage.setRespMessage(message);
            
        } else {
            message.setAll(Constants.ErrorCode.USER_INVALID_PARAM_CODE, Constants.ErrorDesc.USER_INVALID_PARAM_CODE + "query param invalid.");
            queryMessage.setList(new ArrayList<TscUserProfile>());
            queryMessage.setRespMessage(message);
        }
        
        return queryMessage;
    }

    @Override
    public RespMessage del(String userId) {
        RespMessage message = new RespMessage();
        if(StringUtils.isNotBlank(userId) && userId.matches("^\\w+$")) {
            Integer code = userProfileDao.del(userId);
            //judge code
            if(code == null || code <= 0) {
                LOGGER.error("del user affect {} rows.", code);
                message.setAll(Constants.ErrorCode.USER_NOTEXISTS_CODE, new StringBuilder(Constants.ErrorDesc.USER_NOTEXISTS_CODE).append(", userId:").append(userId).toString());
                return message;
            } else {
                message.setMessage(Constants.SUCCESS_DESC);
                LOGGER.info("del user {} success.", userId);
            }
        } else {
            LOGGER.error("userId is invalid.");
            message.setAll(Constants.ErrorCode.USER_INVALID_PARAM_CODE, Constants.ErrorDesc.USER_INVALID_PARAM_CODE + "userId is invalid.");
            return message;
        }
        
        return message;
    }

    @Override
    public RespMessage update(TscUserProfile userProfile) {
        RespMessage message = new RespMessage();
        InvalidationUtil<TscUserProfile> iu = new InvalidationUtil<TscUserProfile>();
        //object is null?
        if(userProfile == null) {
            LOGGER.error("userProfile is null");
            message.setAll(Constants.ErrorCode.USER_INVALID_PARAM_CODE, Constants.ErrorDesc.USER_INVALID_PARAM_CODE + "userProfile is null");
            return message;
        }
        //parameter validation
        Set<ConstraintViolation<TscUserProfile>> constraintViolations = validator.validate(userProfile);
        int size = constraintViolations.size();
        if(size > 0) {
            String msg = iu.formatInvalidationInfo(constraintViolations);
            LOGGER.error("TscUserProfile data validation failed message:{}", msg);
            message.setAll(Constants.ErrorCode.USER_INVALID_PARAM_CODE, Constants.ErrorDesc.USER_INVALID_PARAM_CODE + msg);
            return message;
        }
        
        if(queryByUserId(userProfile.getUserId()) == null) {
            message.setAll(Constants.ErrorCode.USER_NOTEXISTS_CODE, Constants.ErrorDesc.USER_NOTEXISTS_CODE);
            return message;
        }
        //If there is a persistent instance with the same identifier, an exception is thrown
        userProfileDao.update(userProfile);
        message.setMessage(Constants.SUCCESS_DESC);
        LOGGER.info("update user success.userId: {}", userProfile.getUserId());
        
        return message;
    }

}
