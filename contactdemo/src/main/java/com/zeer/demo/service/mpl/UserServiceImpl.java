package com.zeer.demo.service.mpl;


import com.zeer.demo.bean.User;
import com.zeer.demo.mapper.UserMapper;
import com.zeer.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    public User getById(long id){
        return userMapper.getById(id);
    }



    public User getByPP(String phoneNumber,String password){
        User user=null;
        System.out.println(password+" "+phoneNumber);
        user= userMapper.getByPP(phoneNumber,password);

        return user;

    }

    public void insert(User user) {
            userMapper.insert(user);
    }
    public User getByPhoneNumber(String phoneNumber){
        return userMapper.getByPhoneNumber(phoneNumber);
    }
    public void upDatePa(long id,String newPassword){
        userMapper.upDatePa(id,newPassword);
    }
    public User getByPa(long id,String password){
        return userMapper.getByPa(id, password);
    }


    public List<User> getByUsername(String username) {
        return userMapper.getByUsername(username);
    }


    public List<User> getByStudentID(String studentID) {
        return userMapper.getByStudentID(studentID);
    }

    public List<User> getAll() {
        return userMapper.getAll();
    }


}
