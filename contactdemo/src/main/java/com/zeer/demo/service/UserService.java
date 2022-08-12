package com.zeer.demo.service;


import com.zeer.demo.bean.User;

import java.util.List;

public interface UserService {

     User getById(long id);
     User getByPP(String phoneNumber,String password);
     void insert(User user);
     User getByPhoneNumber(String phoneNumber);
     void upDatePa(long id,String newPassword);
      User getByPa(long id,String password);
    List<User> getByUsername(String username);
    List<User> getByStudentID(String studentID);
    List<User> getAll();





}
