package com.bee.sample.ch1.controller;

import com.bee.sample.ch1.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 14530
 *
 * 这里使用String类型的age，让spring帮忙强转成integer
 * {
 *     "age": "2",
 *     "name": "11"
 * }
 *
 */
@RestController
@RequestMapping("/api/v1") 
public class StringCast2IntegerTestCrontroller {

	@PostMapping("/param/test")
	public String receiveStringParamTest(@RequestBody User user) {
		System.out.println(user.toString());
		return "ok";
	}
	
}
