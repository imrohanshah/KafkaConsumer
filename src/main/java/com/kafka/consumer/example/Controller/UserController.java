package com.kafka.consumer.example.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.consumer.example.Model.UserEntity;
import com.kafka.consumer.example.Service.UserService;
import org.elasticsearch.common.inject.internal.Stopwatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public String saveUser(@RequestBody UserEntity user) throws JsonProcessingException {
        return userService.save(user);
    }


    @PostMapping("/all")
    public List<UserEntity> all() throws JsonProcessingException {
        return userService.all();
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestBody UserEntity user){
        userService.delete(user);
    }

    @PostMapping("/deleteAll")
    public void deleteAllUser(){
        userService.deleteAll();
    }

    @PostMapping("/reorder")
    public void reorder(@RequestBody UserEntity user){
        userService.reorder(user);
    }

    @PostMapping("/reorderRanks")
    public void reorderRanks(@RequestBody UserEntity user){
        userService.reorderRanks(user);
    }

    @PostMapping("reorderTest")
    public void reorderTest(@RequestBody UserEntity user){
        userService.reorderFull(user);
    }

    @PostMapping("reorderRanksFull")
    public void reorderRanksFull(@RequestBody UserEntity user){
        userService.reorderRanksFull(user);
    }

    @PostMapping("setup")
    public void setup(){
        userService.setup();
    }

    @PostMapping("test")
    public void test(){
        deleteAllUser();
        setup();
        UserEntity user = new UserEntity();
        user.setPreviousRank(10f);
        user.setRank(3f);
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            reorderRanks(user);
        }
        System.out.println("Time taken for 1 = " + (System.currentTimeMillis() - startTime1));
        deleteAllUser();
        setup();
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            reorderRanksFull(user);
        }
        System.out.println("Time taken for 1 = " + (System.currentTimeMillis() - startTime2));

    }

}
