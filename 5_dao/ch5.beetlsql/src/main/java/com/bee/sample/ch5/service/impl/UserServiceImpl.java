package com.bee.sample.ch5.service.impl;

import java.util.List;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bee.sample.ch5.dao.UserDao;
import com.bee.sample.ch5.entity.User;
import com.bee.sample.ch5.service.UserService;

/**
 * @author 14530
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired UserDao userDao;

	@Autowired
	private SQLManager sqlManager;

	@Override
	public User getUserById(Integer id) {
		return userDao.unique(id);
	}

	@Override
	public List<User> select(String name) {
		User paras = new User();
		paras.setName(name);
		return userDao.selectSample(paras);
	}
}
