package com.bee.sample.ch3.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bee.sample.ch3.entity.User;
import com.bee.sample.ch3.service.UserService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 14530
 * 事务管理
 */
@Service
//@Transactional
public class UserServiceImpl implements UserService {

    @Override
    public List<User> allUser() {
        return sampleUser(5);
    }

    @Override
    public User getUserById(Long id) {
        User user = sampleUser(1).get(0);
        user.setId(id);
        return user;
    }

    private List<User> sampleUser(int num) {
        List<User> list = new ArrayList<User>(num);
        for (int i = 0; i < num; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("mame" + i);
            list.add(user);
        }
        return list;
    }

    /**
     * 如果方法抛出runtimeException，事务会自动回滚，否则事务提交
     * 调用过程中，如果调用其他service方法都会处于事务上下文中
     * @param id
     * @param type
     */
    @Transactional
    @Override
    public void updateUser(Long id, Integer type) {
        // TODO Auto-generated method stub
        newTransaction(1L, 1);
    }

    /**
     * 无论调用方是否正常，都会将数据保存
     * @param id
     * @param type
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void newTransaction(Long id, Integer type) {
        // TODO Auto-generated method stub

    }
}
