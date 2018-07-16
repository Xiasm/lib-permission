package com.xsm.lib_permission.core;

import java.util.ArrayList;

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
     *被拒绝 点击了不再提示或直接拒绝
     */
    void denied(ArrayList<String> refusedPermissions);
}
