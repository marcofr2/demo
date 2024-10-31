package com.example.demo.Service;

import com.example.demo.repository.LogInLogOutTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogInLogOutTimeService {
    @Autowired
    private LogInLogOutTimeRepository logInLogOutTimeRepository;

}
