package com.unigpt.chat.serviceimpl;

import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import com.unigpt.chat.dto.UpdateUserInfoRequestDTO;
import com.unigpt.chat.model.User;
import com.unigpt.chat.repository.UserRepository;
import com.unigpt.chat.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(Integer id, UpdateUserInfoRequestDTO dto) {
        if (userRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User(id, dto.getName(), dto.getAvatar());
        userRepository.save(user);
    }

    @Override
    public void updateUserInfo(Integer targetUserId,
            UpdateUserInfoRequestDTO updateUserInfoRequestDTO)
            throws AuthenticationException {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        targetUser.setName(updateUserInfoRequestDTO.getName());
        targetUser.setAvatar(updateUserInfoRequestDTO.getAvatar());

        userRepository.save(targetUser);
    }
}
