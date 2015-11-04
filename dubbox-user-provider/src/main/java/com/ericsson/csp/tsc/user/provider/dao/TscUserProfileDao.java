package com.ericsson.csp.tsc.user.provider.dao;

import java.util.List;

import com.ericsson.csp.tsc.api.pojo.TscUserProfile;


public interface TscUserProfileDao {
    public String save(TscUserProfile userProfile);
    
    public TscUserProfile queryById(String id);
    
    public List<TscUserProfile> queryByVin(String vin);
    
    public Integer del(String userId);
    
    public void update(TscUserProfile userProfile);
}
