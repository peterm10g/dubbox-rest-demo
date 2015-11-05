package top.crowley.dubbox.rest.demo.provider.service;

import top.crowley.dubbox.rest.demo.api.pojo.QueryRespMessage;
import top.crowley.dubbox.rest.demo.api.pojo.RespMessage;
import top.crowley.dubbox.rest.demo.api.pojo.TscUserProfile;

public interface UserService {
    public RespMessage save(TscUserProfile userProfile) throws Exception;
    
    public QueryRespMessage<TscUserProfile> query(String arg) throws Exception;
    
    public TscUserProfile queryByUserId(String userId);
    
    public RespMessage del(String userId) throws Exception;
    
    public RespMessage update(TscUserProfile userProfile) throws Exception;
}
