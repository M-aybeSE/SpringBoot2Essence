package com.bee.sample.ch4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bee.sample.ch4.entity.User;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DataBindController {

	@Autowired ObjectMapper mapper;

	@RequestMapping("/updateUsers.json")
	public @ResponseBody String say(@RequestBody List<User> list) {
		StringBuilder sb = new StringBuilder();
		for (User user : list) {
			sb.append(user.getName()).append(" ");
		}
		return sb.toString();
	}

	/**
	 * List 对象中的元素并非是 User ，而是包含了一个 key 是id 和 name 的Map ，这是因为 Jackson
	 * 并不知道要把 jsonInput 反序列化成 User 对象。在运行时刻，泛型己经被擦除了（不同于方法
	 * 参数定义的泛型，不会被擦除〉 为了提供泛型信息， Jackson 提供了 JavaType ，用来指明集合
	 * 类型 ，比如应用可以提供一个通用 getCollectionType:
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping("/customize.json")
	public @ResponseBody String customize() throws JsonParseException, JsonMappingException, IOException {
		String jsonInput = "[{\"id\":2,\"name\":\"xiandafu\"},{\"id\":3,\"name\":\"lucy\"}]";
		JavaType type = getCollectionType(List.class, User.class);
		List<User> list = mapper.readValue(jsonInput, type);
		return String.valueOf(list.size());
	}

	public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
	
	@JsonView(User.IdView.class)
	@RequestMapping("/id.json")
	public @ResponseBody User queryIds() {
		User user = new User();
		user.setId(1);
		user.setName("hell");
		return user;
	}
	
	@RequestMapping("/dept.json")
	public @ResponseBody Department getDepartment() {
		return new Department(1);
	}
	
	class Department {
		Map map = new HashMap();
		int id ;
		public Department(int id) {
			this.id = id;
			map.put("newAttr", 1);
		}
		@JsonAnyGetter
		public Map<String, Object> getOtherProperties() {
		  return map;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	}
}
