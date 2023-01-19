package com.example.donfood.service.orderService;

import com.example.donfood.dto.orderDTO.OrderRequestDTO;
import com.example.donfood.dto.orderDTO.OrderUpdateDTO;
import com.example.donfood.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    Order create(OrderRequestDTO orderRequestDTO);
    List<Order> getAll();
    Order getById(Integer id);
    Order update(Integer id, OrderUpdateDTO orderUpdateDTO);
    void delete(Integer id);
}
