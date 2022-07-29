package com.yang.service;

import com.yang.pojo.Admin;
import com.yang.pojo.Permission;

import java.util.List;


public interface AdminService {
    Admin findAdminByUserName(String username);
    List<Permission> findPermissionsByAdminId(Long adminId);

}
