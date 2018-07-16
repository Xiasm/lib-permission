package com.xsm.lib_permission.core;

/**
 * Author: 夏胜明
 * Date: 2018/7/16 0016
 * Email: xiasem@163.com
 * Description:
 */
public interface IPermission {
    /**
     * 已经授权
     */
    void ganted();

    /**
     * 取消授权
     */
    void cancled();

    /**
     *被拒绝 点击了不再提示
     */
    void denied();
}
