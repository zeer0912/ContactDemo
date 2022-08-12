package com.zeer.demo.mapper;

import com.zeer.demo.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //    private String phoneNumber;

//    private String password;
    //    private String username;
//    private String studentID;
//    private String gender;

    @Select("select * from user")
    public List<User> getAll();
    @Select("select * from user where id=#{id}")
    public User getById(long id);
    @Select("select * from user where phoneNumber=#{phoneNumber}")
    public User getByPhoneNumber(String phoneNumber);
    @Select("select * from user where id=#{id} and password=HEX(AES_ENCRYPT(#{password}, \"wifi\"))")
    public User getByPa(long id,String password);
    @Select("select * from user where username=#{username}")
    public List<User> getByUsername(String username);
    @Select("select * from user where studentID=#{studentID}")
    public List<User> getByStudentID(String studentID);

    @Select("SELECT * FROM user WHERE phoneNumber=#{phoneNumber} and password=HEX(AES_ENCRYPT(#{password}, \"wifi\"))")
    public User getByPP(String phoneNumber,String password);


//    @Insert("INSERT INTO user(phoneNumber,password,username,studentID) VALUES(#{user.getPhoneNumber()}," +
//            "HEX(AES_ENCRYPT(#{user.getPassword()}, \"wifi\"))," +
//            "#{user.getUsername()},#{user.getStudentID()})")
    @Insert("INSERT INTO user(phoneNumber,password,username,studentID) VALUES(#{phoneNumber}," +
            "HEX(AES_ENCRYPT(#{password}, \"wifi\"))," +
            "#{username},#{studentID})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public void insert(User user);

    @Update("update user set password=HEX(AES_ENCRYPT(#{newPassword}, \"wifi\")) where id=#{id}")
    public void upDatePa(long id,String newPassword);

}
