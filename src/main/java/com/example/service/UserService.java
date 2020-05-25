package com.example.service;


import com.example.controller.LoginController;
import com.example.data.UserDataAccessService;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    UserDataAccessService userDataAccessService;

   /* @Autowired
    private LoginController loginController;*/



    public List<User> getAllUsers() {
        System.out.println("test  " +userDataAccessService.findAll());
        return userDataAccessService.findAll();
    }


    public User getUserbyID(int userID) {
        return userDataAccessService.getUserbyID(userID);
    }


    public void addNewUser(User user) {
        userDataAccessService.setUser(user);
    }


    public void updateUser(int userId, User user) {
        userDataAccessService.updateUser(userId, user);
    }


    public boolean deleteUser(int userID) {
        return userDataAccessService.deleteUser(userID);
    }


}
