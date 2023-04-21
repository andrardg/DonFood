package com.example.donfood.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    @RequestMapping({ "/api", "/api/", "/api/home"})
    public ModelAndView getHome(){
        return new ModelAndView("home");
    }

    @GetMapping("/api/access_denied")
    public ModelAndView accessDeniedPage(){ return new ModelAndView("accessDenied"); }
}
