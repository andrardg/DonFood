package com.example.donfood.controller;

import com.example.donfood.dto.ongDTO.ONGResponseDTO;
import com.example.donfood.dto.ongDTO.ONGUpdateDTO;
import com.example.donfood.mapper.ONGMapper;
import com.example.donfood.service.ongService.ONGService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/ong")
public class ONGController {
    @Autowired
    private ONGService ongService;

    @GetMapping
    //public ResponseEntity<List<ONGResponseDTO>> getAll(){
    public ModelAndView getAll(){
        ModelAndView modelAndView = new ModelAndView("ongList");
        List<ONGResponseDTO> ongs = ongService.getAll();
        modelAndView.addObject("ongs",ongs);
        return modelAndView;
        //return ResponseEntity.ok().body(ongService.getAll());
    }

    @GetMapping("/{id}")
    //public ResponseEntity<ONGResponseDTO> getById(@PathVariable Integer id){
    public ModelAndView getById(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("ongDetails");
        ONGResponseDTO ong = ongService.getById(id);
        modelAndView.addObject("ong",ong);
        return modelAndView;
        //return ResponseEntity.ok().body(ongService.getById(id));
    }

    @GetMapping("/fullname/{fullName}")
    public ResponseEntity<List<ONGResponseDTO>> getByFullName(@PathVariable String fullName){
        return ResponseEntity.ok().body(ongService.getByFullName(fullName));
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView ongEdit(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("ongForm");
        ONGResponseDTO ongResponseDTO = ongService.getById(id);
        ONGUpdateDTO ongUpdateDTO  = ONGMapper.responseToUpdate(ongResponseDTO);
        modelAndView.addObject("ong", ongResponseDTO);
        modelAndView.addObject("ongUpdate", ongUpdateDTO);
        return modelAndView;
    }
    @PostMapping("/{id}")
    //public ResponseEntity<ONGResponseDTO> update(@PathVariable Integer id, @RequestBody ONGUpdateDTO ongUpdateDTO){
    public ModelAndView update(@PathVariable Integer id, @Valid @ModelAttribute @RequestBody ONGUpdateDTO ongUpdateDTO,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("restaurantForm");
            return modelAndView;
        }
        ongService.update(id, ongUpdateDTO);
        ModelAndView modelAndView = new ModelAndView("ongDetails");
        ONGResponseDTO ongResponseDTO = ongService.getById(id);
        modelAndView.addObject("ong",ongResponseDTO);
        return modelAndView;
        //return ResponseEntity.ok().body(ongService.update(id, ongUpdateDTO));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id){
        ongService.delete(id);
        return new ModelAndView("redirect:" + "/logout");
    }
}
