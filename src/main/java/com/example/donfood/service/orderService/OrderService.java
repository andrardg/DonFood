package com.example.donfood.service.orderService;

import com.example.donfood.dto.orderDTO.OrderRequestDTO;
import com.example.donfood.dto.orderDTO.OrderUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.DonationMapper;
import com.example.donfood.mapper.OrderMapper;
import com.example.donfood.model.Donation;
import com.example.donfood.model.Order;
import com.example.donfood.model.enums.Status;
import com.example.donfood.repository.IDonationRepository;
import com.example.donfood.repository.IONGRepository;
import com.example.donfood.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService implements IOrderService{

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IONGRepository ongRepository;
    @Autowired
    private IDonationRepository donationRepository;

    @Override
    public Order create(OrderRequestDTO orderRequestDTO) {
        if(!ongRepository.existsById(orderRequestDTO.getOngId()))
            throw new ResourceNotFoundException("No ong with that id.");

        if(!donationRepository.existsById(orderRequestDTO.getDonationId()))
            throw new ResourceNotFoundException("No donation with that id.");
        Donation donation = donationRepository.findById(orderRequestDTO.getDonationId()).get();

        orderRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        orderRequestDTO.setStatus(Status.NEW);
        Order order = OrderMapper.requestToOrder(orderRequestDTO);

        double quantity = donation.getQuantity();
        List<Order> orders = orderRepository.findAll();
        for(Order x : orders)
            if(x.getDonation().getDonationId() == donation.getDonationId())
                quantity = quantity - x.getQuantitySelected();

        if(quantity - order.getQuantitySelected() < 0)
            throw new IllegalArgumentException("Quantity exceeds available stock");

        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Integer id) {
        if(!orderRepository.existsById(id))
            throw new ResourceNotFoundException("No Order with that id.");
        Order order = orderRepository.findById(id).get();
        return order;
    }

    @Override
    public Order update(Integer id, OrderUpdateDTO orderUpdateDTO) {
        if(!orderRepository.existsById(id))
            throw new ResourceNotFoundException("No Order with that id.");
        Order dbOrder = orderRepository.findById(id).get();

        if(orderUpdateDTO.getStatus() != null)
            dbOrder.setStatus(orderUpdateDTO.getStatus());
        if(orderUpdateDTO.getQuantitySelected() != null && orderUpdateDTO.getQuantitySelected() > dbOrder.getQuantitySelected()){

            Donation donation = donationRepository.findById(dbOrder.getDonation().getDonationId()).get();
            if(donation == null)
                throw new ResourceNotFoundException("No donation with that id.");

            double quantity = donation.getQuantity();
            List<Order> orders = orderRepository.findAll();
            for(Order x : orders)
                if(x.getDonation().getDonationId() == donation.getDonationId() && x.getOrderId() != dbOrder.getOrderId())
                    quantity = quantity - x.getQuantitySelected();

            if(quantity - orderUpdateDTO.getQuantitySelected() < 0)
                throw new IllegalArgumentException("Quantity exceeds available stock");

            dbOrder.setQuantitySelected(orderUpdateDTO.getQuantitySelected());
        }
        else if(orderUpdateDTO.getQuantitySelected() != null && orderUpdateDTO.getQuantitySelected() < dbOrder.getQuantitySelected())
            dbOrder.setQuantitySelected(orderUpdateDTO.getQuantitySelected());

        orderRepository.save(dbOrder);
        return dbOrder;
    }

    @Override
    public void delete(Integer id) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!orderRepository.existsById(id))
            throw new ResourceNotFoundException("The Order with id " + id + " was not found");

        orderRepository.deleteById(id);
    }
}
