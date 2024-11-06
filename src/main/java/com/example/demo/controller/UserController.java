package com.example.demo.controller;

import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Service.UserService;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findByUid")
    public User findUserByUid(@RequestParam String uid) throws UserNotFoundException {
        return userService.findUserByUid(uid)
                .orElseThrow(() -> new UserNotFoundException("User not found with uid " + uid));
    }

    @GetMapping("/logInOrlogOut")
    public String logAttendance(@RequestParam String uid) throws UserNotFoundException {
        return userService.logUserAttendance(uid);
    }

    @GetMapping("/getTotalWorkHours")
    public String getWorkingHours(@RequestParam String uid) throws UserNotFoundException {
        return  userService.getTotalWorkingHours(uid).get().toString();
    }

    @PutMapping("/addUser")
    public String saveUser(@ModelAttribute User user){
        userRepository.save(user);
        return "user was saved";
    }

    @GetMapping("/checkWorkHours")
    public String checkWorkHours(@RequestParam String uid, @RequestParam LocalDate date) throws UserNotFoundException {
        boolean hasWorkedEightHours = userService.CheckIfUserHasWorkedEnough(uid,date);
        if (hasWorkedEightHours) {
            return "User has worked at least 8 hours on "
                    + date
                    + userService.getTotalWorkingHoursSpecifiedDay(uid, date).get().toString();
        } else {
            return "User has not completed 8 hours on "
                    + date
                    + userService.getTotalWorkingHoursSpecifiedDay(uid, date).get().toString();
        }
    }

}
