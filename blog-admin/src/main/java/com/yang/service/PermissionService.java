package com.yang.service;

import com.yang.params.PageParam;
import com.yang.pojo.Permission;
import com.yang.vo.Result;

public interface PermissionService {
    Object listPermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
