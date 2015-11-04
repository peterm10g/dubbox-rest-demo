package com.ericsson.csp.tsc.api.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.protocol.rest.RpcExceptionMapper;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.ericsson.csp.tsc.api.pojo.RespMessage;

public class ValidationExceptionMapper extends RpcExceptionMapper {
    
    private static Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionMapper.class);
    
    @Override
    protected Response handleConstraintViolationException(ConstraintViolationException cve) {
        RespMessage message = new RespMessage();
        StringBuffer errorMsg = new StringBuffer();
        for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
            try {
                if (message.getStatusCode() == 0) {
                    message.setStatusCode(cv.getRootBeanClass().getField("invalid_code").getInt(cv.getRootBean()));
                }
            } catch (Exception e) {
                LOGGER.error("reflect error: {}", e.getMessage());
            }
            errorMsg.append(cv.getMessage());
        }
        message.setMessage(errorMsg.toString());
        // json install xml
        return Response.status(Response.Status.BAD_REQUEST).entity(message.toString())
                .type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}
