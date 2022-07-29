package com.mszl.blog.service;

import com.mszl.blog.pojo.SysUser;
import com.mszl.blog.vo.params.UserVo;

public interface SysUserService {


    UserVo findUserVoById(Long id);

    SysUser findUser(String account, String password);


    SysUser findUserById(Long id);

    Object findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

}
