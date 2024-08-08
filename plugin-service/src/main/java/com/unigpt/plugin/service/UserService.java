package com.unigpt.plugin.service;

import com.unigpt.plugin.dto.ResponseDTO;

public interface UserService {
    ResponseDTO createUser(Integer userid, String name, String account);
}
