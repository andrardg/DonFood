package com.example.donfood.controller;

import com.example.donfood.dto.donationDTO.DonationRequestDTO;
import com.example.donfood.dto.donationDTO.DonationUpdateDTO;
import com.example.donfood.model.Donation;
import com.example.donfood.service.donationService.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donation")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @GetMapping
    public ResponseEntity<List<Donation>> getAll() {
        return ResponseEntity.ok().body(donationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(donationService.getById(id));
    }

    @GetMapping("/product/{product}")
    public ResponseEntity<List<Donation>> getByProduct(@PathVariable String product){
        return ResponseEntity.ok().body(donationService.findByProduct(product));
    }

    @GetMapping("/{quantity}/{product}")
    public ResponseEntity<List<Donation>> getByQuantityAndProduct(@PathVariable Double quantity, @PathVariable String product){
        return ResponseEntity.ok().body(donationService.getByQuantityAndProduct(quantity, product));
    }

    @PostMapping
    public ResponseEntity<Donation> create(@RequestBody DonationRequestDTO donationRequestDTO){
        return ResponseEntity.ok().body(donationService.create(donationRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donation> update(@PathVariable Integer id, @RequestBody DonationUpdateDTO donationUpdateDTO){
        return ResponseEntity.ok().body(donationService.update(id, donationUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        donationService.delete(id);
    }
}
