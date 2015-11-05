package top.crowley.dubbox.rest.demo.provider.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import top.crowley.dubbox.rest.demo.api.pojo.TscUserProfile;
import top.crowley.dubbox.rest.demo.provider.dao.BaseDao;
import top.crowley.dubbox.rest.demo.provider.dao.UserProfileDao;

@Repository
public class UserProfileDaoImpl extends BaseDao implements UserProfileDao{

    @Override
    public String save(TscUserProfile userProfile) {
        saveObj(userProfile);
        return userProfile.getUserId();
    }

    @Override
    public Integer del(String userId) {
        String hql = "delete from TscUserProfile s where s.userId = ?";
        return getCurrentSession().createQuery(hql).setString(0, userId).executeUpdate();
    }

    @Override
    public void update(TscUserProfile userProfile) {
        merge(userProfile);
    }

    @Override
    public TscUserProfile queryById(String userId) {
        Criteria cri = getCurrentSession().createCriteria(TscUserProfile.class);
        cri.add(Restrictions.eq("userId", userId));
        TscUserProfile user = (TscUserProfile) cri.uniqueResult();
        return user;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<TscUserProfile> queryByVin(String vin) {
        Criteria cri = getCurrentSession().createCriteria(TscUserProfile.class);
        cri.add(Restrictions.eq("vin", vin));
        List<TscUserProfile> users = (List<TscUserProfile>) cri.list();
        return users;
    }

}
