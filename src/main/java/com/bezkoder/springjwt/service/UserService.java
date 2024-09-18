package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.User;

import java.util.List;

public interface UserService {
    User findUsersById(Long id);
}
