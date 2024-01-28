package com.example.donfood.controller;

import com.example.donfood.model.Account;
import com.example.donfood.repository.IAccountRepository;
import com.example.donfood.repository.IONGRepository;
import com.example.donfood.repository.IRestaurantRepository;
import com.example.donfood.service.accoutService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IONGRepository ongRepository;
    @Autowired
    private IRestaurantRepository restaurantRepository;
    @RequestMapping({ "/api", "/api/", "/api/home"})
    public ModelAndView getHome(){
        return new ModelAndView("home");
    }

    @GetMapping("/api/access_denied")
    public ModelAndView accessDeniedPage(){ return new ModelAndView("accessDenied"); }


    @GetMapping("/profile")
    public ModelAndView profilePage(){
        ModelAndView modelAndView = new ModelAndView("profile");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountRepository.findByEmail(authentication.getName());
        String email = account.getEmail();
        if(ongRepository.existsByAccountONG_Email(email))
            modelAndView.addObject("objectId",ongRepository.findByAccountONGEmail(email).getOngId());
        else if(restaurantRepository.existsByAccountRest_Email(email))
            modelAndView.addObject("objectId",restaurantRepository.findByAccountRestEmail(email).getRestaurantId());
        return modelAndView;
    }
}
