package com.cn.serviceImpl;

import javax.annotation.Resource;  

import org.springframework.stereotype.Service;  
  
import com.cn.dao.UserDao;  
import com.cn.domain.User;  
import com.cn.service.UserService; 

@Service("userService")  
public class UserServiceImpl implements UserService {  
    @Resource  
    private UserDao userDao;  
    @Override  
    public User getUserById(int userId) {  
        // TODO Auto-generated method stub  
        return this.userDao.selectByPrimaryKey(userId);  
    }  
}
