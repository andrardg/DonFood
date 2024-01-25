package com.example.donfood.controller;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.dto.ongDTO.ONGRequestDTO;
import com.example.donfood.dto.ongDTO.ONGResponseDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantRequestDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.model.Account;
import com.example.donfood.service.accoutService.AccountService;
import com.example.donfood.service.ongService.ONGService;
import com.example.donfood.service.restaurantService.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;

@RestController
@RequestMapping("")
@Slf4j
public class AccountController {
    @Autowired
    private ONGService ongService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/api/registerONG")
    public ResponseEntity<ONGResponseDTO> registerONG(@RequestBody ONGRequestDTO ongRequestDTO){
        return ResponseEntity.ok().body(ongService.register(ongRequestDTO));
    }

    @GetMapping("/api/registerRestaurantForm")
    public ModelAndView registerRestaurantForm(){
        if(!accountService.isAuthenticated()){
            ModelAndView modelAndView = new ModelAndView("registerRestaurant");
            AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
            RestaurantRequestDTO restaurantRequestDTO = new RestaurantRequestDTO();
            restaurantRequestDTO.setAccountRequestDTO(accountRequestDTO);
            modelAndView.addObject("restaurant", restaurantRequestDTO);
            return modelAndView;
        }
        else{
            ModelAndView modelAndView = new ModelAndView("redirect:/api");
            return modelAndView;
        }
    }
    @PostMapping("/api/registerRestaurant")
    public ModelAndView registerRestaurant(@Valid @ModelAttribute("restaurant") RestaurantRequestDTO restaurantRequestDTO,
                                           BindingResult bindingResultRestaurant){
        if (bindingResultRestaurant.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("registerRestaurant");
            modelAndView.addObject("restaurant", restaurantRequestDTO);
            modelAndView.addObject("errorMessage", "Invalid data");
            return modelAndView;
        }

        try{
            RestaurantResponseDTO restaurantResponseDTO = restaurantService.register(restaurantRequestDTO);
            ModelAndView modelAndView = new ModelAndView("redirect:/login");
            //JOptionPane.showMessageDialog(null, "Registered successfully", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
            return modelAndView;

        }catch(Exception e){
            ModelAndView modelAndView = new ModelAndView("registerRestaurant");
            modelAndView.addObject("restaurant", restaurantRequestDTO);
            modelAndView.addObject("errorMessage", e.getMessage());
            return modelAndView;
        }
    }
    @GetMapping("/api/login")
    public ModelAndView loginForm() {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       // if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
        if(!accountService.isAuthenticated()){
            ModelAndView modelAndView = new ModelAndView("login");
            AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
            modelAndView.addObject("account", accountRequestDTO);
            return modelAndView;
        }
        else{
            ModelAndView modelAndView = new ModelAndView("redirect:/api");
            return modelAndView;
        }
    }
    @PostMapping("/api/perform_login")
    public ModelAndView login( @Valid @ModelAttribute AccountRequestDTO accountRequestDTO,
                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("account", accountRequestDTO);
            modelAndView.addObject("errorMessage", "Email or password invalid");
            return modelAndView;
        }
        try{
            Account loggedInAccount = accountService.login(accountRequestDTO);
            ModelAndView modelAndView = new ModelAndView("redirect:/api");
            modelAndView.addObject("loggedInAccount",loggedInAccount);
            return modelAndView;

        }catch(Exception e){
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("account", accountRequestDTO);
            modelAndView.addObject("errorMessage", e.getMessage());
            return modelAndView;
        }
    }
    @GetMapping("/logout")
    public ModelAndView logout(@CurrentSecurityContext(expression="authentication") Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()){
        //if(accountService.isAuthenticated()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info(auth.getName() + " was logged out inside controller");
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        else{
            log.info("Not authenticated");
        }
        ModelAndView view = new ModelAndView("redirect:/api/login");
        return view;
    }


}
