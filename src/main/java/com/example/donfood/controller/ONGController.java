package com.example.donfood.controller;

import com.example.donfood.dto.ongDTO.ONGResponseDTO;
import com.example.donfood.dto.ongDTO.ONGUpdateDTO;
import com.example.donfood.service.ongService.ONGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ong")
public class ONGController {
    @Autowired
    private ONGService ongService;

    @GetMapping
    public ResponseEntity<List<ONGResponseDTO>> getAll(){
        return ResponseEntity.ok().body(ongService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ONGResponseDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(ongService.getById(id));
    }

    @GetMapping("/fullname/{fullName}")
    public ResponseEntity<List<ONGResponseDTO>> getByFullName(@PathVariable String fullName){
        return ResponseEntity.ok().body(ongService.getByFullName(fullName));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ONGResponseDTO> update(@PathVariable Integer id, @RequestBody ONGUpdateDTO ongUpdateDTO){
        return ResponseEntity.ok().body(ongService.update(id, ongUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        ongService.delete(id);
        return "Ok";
    }
}
