package com.example.donfood.controller;

import com.example.donfood.dto.orderDTO.OrderRequestDTO;
import com.example.donfood.dto.orderDTO.OrderUpdateDTO;
import com.example.donfood.model.Donation;
import com.example.donfood.model.Order;
import com.example.donfood.service.orderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAll(){
        return ResponseEntity.ok().body(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(orderService.getById(id));
    }

    @PostMapping
    public  ResponseEntity<Order> create(@RequestBody OrderRequestDTO orderRequestDTO){
        return ResponseEntity.ok().body(orderService.create(orderRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Integer id, @RequestBody OrderUpdateDTO orderUpdateDTO) {
        return ResponseEntity.ok().body(orderService.update(id, orderUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        orderService.delete(id);
        return "Ok";
    }
}
