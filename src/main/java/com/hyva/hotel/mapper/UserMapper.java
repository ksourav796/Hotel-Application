package com.hyva.hotel.mapper;

import com.hyva.hotel.entities.User;
import com.hyva.hotel.pojo.UserPojo;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User mapPojoToEntity(UserPojo userPojo) {
        User user = new User();
        user.setUseraccountId(userPojo.getUseraccountId());
        user.setEmail(userPojo.getEmail());
        user.setUserName(userPojo.getUserName());
        user.setPasswordUser(userPojo.getPasswordUser());
        user.setFull_name(userPojo.getFull_name());
        user.setPhone(userPojo.getPhone());
        user.setSecurityAnswer(userPojo.getSecurityAnswer());
        user.setSecurityQuestion(userPojo.getSecurityQuestion());
        user.setStatus(userPojo.getStatus());
        return user;
    }
    public static List<UserPojo> mapEntityToPojo(List<User> userList){
        List<UserPojo> list=new ArrayList<>();
        for(User user:userList) {
            UserPojo pojo = new UserPojo();
            pojo.setUseraccountId(user.getUseraccountId());
            pojo.setPhone(user.getPhone());
            pojo.setEmail(user.getEmail());
            pojo.setPasswordUser(user.getPasswordUser());
            pojo.setUserName(user.getUserName());
            pojo.setFull_name(user.getFull_name());
            pojo.setSecurityAnswer(user.getSecurityAnswer());
            pojo.setSecurityQuestion(user.getSecurityQuestion());
            pojo.setStatus(user.getStatus());
            list.add(pojo);
        }
        return list;
    }
}
