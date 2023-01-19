package com.example.donfood.mapper;

import com.example.donfood.dto.orderDTO.OrderRequestDTO;
import com.example.donfood.model.Order;
import org.modelmapper.ModelMapper;

public class OrderMapper {
    public static Order requestToOrder (OrderRequestDTO orderRequestDTO ){
        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderRequestDTO, Order.class);
        return order;
    }
}
