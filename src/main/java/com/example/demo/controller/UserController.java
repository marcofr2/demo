package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findByUid")
    public User findUserByUid(@RequestParam String uid){
        return userService.findUserByUid(uid);
    }

    @GetMapping("/logInOrlogOut")
    public String logAttendance(@RequestParam String uid) {
        return userService.logUserAttendance(uid);
    }

    @GetMapping("/getTotalWorkHours")
    public String getWorkingHours(@RequestParam String uid){
        return  userService.getTotalWorkingHours(uid).get().toString();
    }

    @PutMapping("/addUser")
    public String saveUser(@ModelAttribute User user){
        userRepository.save(user);
        return "user was saved";
    }
}
