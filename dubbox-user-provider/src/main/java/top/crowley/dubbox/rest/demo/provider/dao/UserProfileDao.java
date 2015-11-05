package top.crowley.dubbox.rest.demo.provider.dao;

import java.util.List;

import top.crowley.dubbox.rest.demo.api.pojo.TscUserProfile;


public interface UserProfileDao {
    public String save(TscUserProfile userProfile);
    
    public TscUserProfile queryById(String id);
    
    public List<TscUserProfile> queryByVin(String vin);
    
    public Integer del(String userId);
    
    public void update(TscUserProfile userProfile);
}
