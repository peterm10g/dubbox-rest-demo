package com.ericsson.csp.tsc.user.provider.service;

import java.util.List;

import com.ericsson.csp.tsc.api.pojo.RespMessage;
import com.ericsson.csp.tsc.api.pojo.TscUserProfile;

public interface UserService {
    public RespMessage save(TscUserProfile userProfile) throws Exception;
    
    public List<TscUserProfile> query(String arg) throws Exception;
    
    public TscUserProfile queryByUserId(String userId);
    
    public RespMessage del(String userId) throws Exception;
    
    public RespMessage update(TscUserProfile userProfile) throws Exception;
}
