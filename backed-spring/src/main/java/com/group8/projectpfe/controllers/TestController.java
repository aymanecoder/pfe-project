package com.group8.projectpfe.controllers;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class TestController {

    @GetMapping
    public String getName(){
        return "hello world";
    }

}
