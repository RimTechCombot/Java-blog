package com.example.demo.services;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public HashMap checkUser(User user)
    {
        HashMap result = new HashMap();
        result.put("status", true);
        if(userRepository.findByUserName(user.getUserName())!=null)
        {
            result.put("status", false);
            result.put("usernameExists", true);
        }
        if(userRepository.findByEmail(user.getEmail())!=null)
        {
            result.put("status", false);
            result.put("emailExists", true);
        }
        return result;
    }

    public void saveUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setRole(new Role(new Long(2)));
        userRepository.save(user);
    }
}
