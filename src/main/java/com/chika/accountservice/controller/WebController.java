package com.chika.accountservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/create")
public class WebController {
    @GetMapping
    public String getView(){
        return "create-account";
    }
}
