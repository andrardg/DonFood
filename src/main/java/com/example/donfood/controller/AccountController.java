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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private ONGService ongService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/registerONG")
    public ResponseEntity<ONGResponseDTO> registerONG(@RequestBody ONGRequestDTO ongRequestDTO){
        return ResponseEntity.ok().body(ongService.register(ongRequestDTO));
    }

    @PostMapping("/registerRestaurant")
    public ResponseEntity<RestaurantResponseDTO> registerRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO){
        return ResponseEntity.ok().body(restaurantService.register(restaurantRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody AccountRequestDTO accountRequestDTO){
        return ResponseEntity.ok().body(accountService.login(accountRequestDTO));
    }
}
