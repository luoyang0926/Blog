package com.mszl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszl.blog.mapper.SysUserMapper;
import com.mszl.blog.pojo.SysUser;
import com.mszl.blog.service.LoginService;
import com.mszl.blog.service.SysUserService;
import com.mszl.blog.vo.ErrorCode;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.LoginUserVo;
import com.mszl.blog.vo.params.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            user=new SysUser();
            user.setId(1L);
            user.setAvatar("/static/img/logo.b3a48c0.png");
            user.setNickname("码神之路");
        }
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return userVo;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser=new SysUser();
            sysUser.setNickname("码神之路");
        }
        return sysUser;
    }

    @Override
    public Object findUserByToken(String token) {
        /**
         * 1.token 合法性校验
         *  是否为空，解析是否成功，redis是否存在
         * 2. 如果校验失败，返回错误信息
         * 3.如果成功，返回对应结果 LoginUserVo
         */

        SysUser user=loginService.checkToken(token);
        if (user == null) {
            return ReturnObject.fail(ErrorCode.Token_NOT_EXIST.getCode(), ErrorCode.Token_NOT_EXIST.getMsg());
        }
        LoginUserVo loginUserVo=new LoginUserVo();
        loginUserVo.setId(user.getId());
        loginUserVo.setNickname(user.getNickname());
        loginUserVo.setAvatar(user.getAvatar());
        loginUserVo.setAccount(user.getAccount());

        return ReturnObject.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit 1");
        return this.sysUserMapper.selectOne(queryWrapper);

    }

    @Override
    public void save(SysUser sysUser) {
        this.sysUserMapper.insert(sysUser);

    }
}
