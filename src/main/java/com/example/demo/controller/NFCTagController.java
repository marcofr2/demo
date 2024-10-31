package com.example.demo.controller;

import com.example.demo.Service.NFCTagService;
import com.example.demo.entity.NFCTag;
import com.example.demo.repository.NFCtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/NFCTag")
public class NFCTagController {
    @Autowired
    private NFCTagService nfcTagService;

    @Autowired
    private NFCtagRepository nfCtagRepository;

    @PostMapping("/addNFC")
    public String addNFC(@ModelAttribute NFCTag nfcTag){
        nfCtagRepository.save(nfcTag);
        return "NFCTag was saved";
    }

}
