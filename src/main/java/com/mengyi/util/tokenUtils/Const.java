package com.mengyi.util.tokenUtils;

/**
 * @author mengyiyouth
 * @date 2020/7/18 11:42
 * token管理常量
 **/
public class Const {
    /**
     * 设置删除标志为真
     */
    public static final Integer DEL_FLAG_TRUE = 1;

    /**
     * 设置删除标志为假
     */
    public static final Integer DEL_FLAG_FALSE = 0;

    /**
     * redis存储token设置的过期时间，1天
     */
    public static final Integer TOKEN_EXPIRE_TIME = 60 * 60 * 24;

    /**
     * 设置可以重置token过期时间的时间界限，12小时
     */
    public static final Integer TOKEN_RESET_TIME = 60 * 60 * 12;
}
