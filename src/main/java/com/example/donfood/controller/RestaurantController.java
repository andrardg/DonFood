package com.example.donfood.controller;

import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantUpdateDTO;
import com.example.donfood.service.restaurantService.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAll(){
        return ResponseEntity.ok().body(restaurantService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(restaurantService.getById(id));
    }

    @GetMapping("/fullname/{fullName}")
    public ResponseEntity<List<RestaurantResponseDTO>> getByFullName(@PathVariable String fullName){
        return ResponseEntity.ok().body(restaurantService.getByFullName(fullName));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> update(@PathVariable Integer id,@RequestBody RestaurantUpdateDTO restaurantUpdateDTO){
        return ResponseEntity.ok().body(restaurantService.update(id, restaurantUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        restaurantService.delete(id);
        return "Ok";
    }
}
