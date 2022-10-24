package com.bee.sample.ch9.service;

import com.bee.sample.ch9.entity.User;

public interface UserService {

    int getCredit(int userId);

    boolean updateUser(User user);
}
