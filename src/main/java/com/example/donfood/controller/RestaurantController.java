package com.example.donfood.controller;

import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantUpdateDTO;
import com.example.donfood.mapper.RestaurantMapper;
import com.example.donfood.service.restaurantService.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    //public ResponseEntity<List<RestaurantResponseDTO>> getAll(){
    public ModelAndView getAll(){
        ModelAndView modelAndView = new ModelAndView("restaurantList");
        List<RestaurantResponseDTO> restaurants = restaurantService.getAll();
        modelAndView.addObject("restaurants",restaurants);
        return modelAndView;
        //return ResponseEntity.ok().body(restaurantService.getAll());
    }

    @GetMapping("/{id}")
    //public ResponseEntity<RestaurantResponseDTO> getById(@PathVariable Integer id){
    public ModelAndView getById(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("restaurantDetails");
        RestaurantResponseDTO restaurant = restaurantService.getById(id);
        modelAndView.addObject("restaurant",restaurant);
        return modelAndView;
        //return ResponseEntity.ok().body(restaurantService.getById(id));
    }

    @GetMapping("/fullname/{fullName}")
    public ResponseEntity<List<RestaurantResponseDTO>> getByFullName(@PathVariable String fullName){
        return ResponseEntity.ok().body(restaurantService.getByFullName(fullName));
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView restaurantEdit(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("restaurantForm");
        RestaurantResponseDTO restaurant= restaurantService.getById(id);
        RestaurantUpdateDTO restaurantUpdateDTO = RestaurantMapper.responseToUpdate(restaurant);
        modelAndView.addObject("restaurant", restaurant);
        modelAndView.addObject("restaurantUpdate", restaurantUpdateDTO);
        //modelAndView.addObject("rights", Arrays.asList(Right.values()));
        return modelAndView;
    }
    @PostMapping("/{id}")
    //public ResponseEntity<RestaurantResponseDTO> update(@PathVariable Integer id,@Valid @ModelAttribute @RequestBody RestaurantUpdateDTO restaurantUpdateDTO){
    public ModelAndView update(@PathVariable Integer id, @Valid @ModelAttribute RestaurantUpdateDTO restaurantUpdateDTO,
                                                        BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("restaurantForm");
            restaurantUpdateDTO.setRestaurantId(id);
            modelAndView.addObject("restaurantUpdate", restaurantUpdateDTO);
            RestaurantResponseDTO restaurant= restaurantService.getById(id);
            modelAndView.addObject("restaurant", restaurant);
            return modelAndView;
        }
        restaurantService.update(id, restaurantUpdateDTO);
        ModelAndView modelAndView = new ModelAndView("restaurantDetails");
        RestaurantResponseDTO restaurant = restaurantService.getById(id);
        modelAndView.addObject("restaurant",restaurant);
        return modelAndView;
    }

    @PostMapping("/{id}/{ongEmail}")
    public ModelAndView addFav(@PathVariable Integer id, @PathVariable String ongEmail){
        RestaurantResponseDTO restaurantResponseDTO = restaurantService.addFav(id, ongEmail);
        ModelAndView modelAndView = new ModelAndView("restaurantDetails");
        modelAndView.addObject("restaurant", restaurantResponseDTO);
        return modelAndView;
    }

    @DeleteMapping("/{id}/{ongEmail}")
    public ModelAndView removeFav(@PathVariable Integer id, @PathVariable String ongEmail){
        RestaurantResponseDTO restaurantResponseDTO = restaurantService.removeFav(id, ongEmail);
        ModelAndView modelAndView = new ModelAndView("restaurantDetails");
        modelAndView.addObject("restaurant", restaurantResponseDTO);
        return modelAndView;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id){
        restaurantService.delete(id);
        return new ModelAndView("redirect:" + "/logout");
    }
}
