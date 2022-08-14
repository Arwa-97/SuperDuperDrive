package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.UserSignupForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username){
        return userMapper.getUser(username) == null;
    }

    public User getUserInfo(String username){
        return this.userMapper.getUser(username);
    }

    public int insertUser(UserSignupForm userForm){
        var isExist = isUsernameAvailable(userForm.getUsername());
        if(isExist){
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String hashedPassword = hashService.getHashedValue(userForm.getPassword(), encodedSalt);

            User user = new User(null, userForm.getUsername(), hashedPassword,
                    encodedSalt, userForm.getFirstname(), userForm.getLastname());

            return userMapper.insertUser(user);
        }
        return 0;
    }
}
