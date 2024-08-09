package com.unigpt.plugin.serviceImpl;

import com.unigpt.plugin.Repository.UserRepository;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.model.User;
import com.unigpt.plugin.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseDTO createUser(Integer userid, String name, String account){
        // check if userid or username already exists
        if(userRepository.findByTrueId(userid).isPresent())
            return new ResponseDTO(false, "User with id " + userid + " already exists");
        if(userRepository.findByName(name).isPresent())
            return new ResponseDTO(false, "User with name " + name + " already exists");

        // create user
        userRepository.save(new User(userid, name, account));
        return new ResponseDTO(true, "User created successfully");
    }
}
