package com.bee.sample.ch11.controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import com.bee.sample.ch11.entity.Baike;
import com.mongodb.client.result.UpdateResult;

@RestController
public class MongoCrontroller {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/baike/{name}")
    public Baike findUser(@PathVariable String name) {
        Baike dict = mongoTemplate.findById(name, Baike.class);
        return dict;
    }

    @GetMapping("/addbaike")
    public Baike addDict(Baike baike) {
        baike.setCrateDate(new Date());
        mongoTemplate.insert(baike);
        return baike;
    }


    @GetMapping("/querybad/{bad}")
    public List<Baike> queryBad(@PathVariable int bad, @RequestParam int good) {
        Criteria criteria = where("comment.bad").gt(bad);

        Criteria criteria2 = where("comment.good").lt(good);

        List<Baike> list = mongoTemplate.find(query(criteria), Baike.class);

        // 连接两个条件的情况
        mongoTemplate.find(query(criteria.andOperator(criteria2)), Baike.class);

        return list;
    }

    @GetMapping("/baike/tag/{tag}")
    public @ResponseBody
    String addOne(@PathVariable String tag) {
        Criteria criteria = where("tag").in(tag);
        Update update = new Update();
        // 自增1
        update.inc("comment.good", 1);
        // 批量修改
        UpdateResult result = mongoTemplate.updateMulti(query(criteria), update, Baike.class);
        return "成功修改 " + result.getModifiedCount();
    }

    @GetMapping("/baike/tag/{tag}/{pageNum}")
    public List<Baike> findBaike(@PathVariable String tag, @PathVariable int pageNum) {
        Criteria criteria = where("tag").in(tag);
        Query query = query(criteria);
        //查询总数
        long totalCount = mongoTemplate.count(query, Baike.class);
        //每页个数 
        int numOfPage = 10;
        //计算总数
        long totalPage = totalCount % numOfPage == 0 ? (totalCount / numOfPage) : (totalCount / numOfPage + 1);

        int skip = (pageNum - 1) * numOfPage;
        // 从 skip 页开始查，查询的记录数为 numOfPage
        query.skip(skip).limit(numOfPage);
        List<Baike> list = mongoTemplate.find(query, Baike.class);
        return list;
    }

    @GetMapping("/updatebaike")
    public Baike updateDict(Baike baike) {
        baike.setUpdateDate(new Date());
        // 如果主键已经存在，则更新；不存在则新增  区别于insert -> 如果主键存在，则报错
        mongoTemplate.save(baike);
        return baike;
    }

    @GetMapping("/deletebaike")
    public Baike deleteDict(String id) {
        Baike baike = new Baike();
        baike.setId(id);
        mongoTemplate.remove(baike);
        return baike;
    }

}
