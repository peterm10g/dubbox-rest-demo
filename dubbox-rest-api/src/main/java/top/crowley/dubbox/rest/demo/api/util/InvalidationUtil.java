package top.crowley.dubbox.rest.demo.api.util;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * 
 * @author wangsy
 *
 */
public class InvalidationUtil<T> {
    public String formatInvalidationInfo(Set<ConstraintViolation<T>> infos) {
        Iterator<ConstraintViolation<T>> it = infos.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            ConstraintViolation<T> t = it.next();
            sb.append(t.getMessage()).append(" ");
        }
        
        return sb.toString();
    }
}