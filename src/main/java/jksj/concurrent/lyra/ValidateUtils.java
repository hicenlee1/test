package jksj.concurrent.lyra;

import java.util.Collection;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by chenwenhao on 2016/11/9.
 */
public final class ValidateUtils {

    private ValidateUtils() {
        super();
    }


    /**
     * @return true Is Find Null Or Blank
     */
    public static boolean check(String value) {
        return StringUtils.isBlank(value);
    }




    /**
     * @return true Is Find Null Or Blank OR 0
     */
    public static boolean check(Long value) {
        return (value == null || (Long.compare(value, 0L) == 0));
    }


    /**
     * @return true Is Find Null Or Blank OR 0
     */
    public static boolean check(Integer value) {
        return (value == null || (Integer.compare(value, 0) == 0));
    }


    /**
     * @return true Is Find Null Or Blank
     */
    public static boolean check(Collection value) {
        return CollectionUtils.isEmpty(value);
    }





    /**
     * @return true Is Find Null Or Blank
     */
    public static boolean check(Map value) {
        if (value == null || value.size() == 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @return true Is Find Null Or Blank
     */
    public static boolean check(Object value) {
        if (value == null) {
            return true;
        } else {
            if (value instanceof Collection) {
                Collection coll = (Collection) value;
                return check(coll);
            } else {
                return false;
            }
        }
    }


    public static boolean check(Object[] a) {
        if (a == null || a.length == 0) {
            return true;
        }
        return false;
    }

}
