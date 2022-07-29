package com.yang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.pojo.Admin;
import com.yang.pojo.Permission;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    List<Permission> findPermissionsByAdminId(Long adminId);
}
