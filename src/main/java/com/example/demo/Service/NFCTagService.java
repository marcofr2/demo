package com.example.demo.Service;

import com.example.demo.repository.NFCtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NFCTagService {
    @Autowired
    private NFCtagRepository nfCtagRepository;
}
