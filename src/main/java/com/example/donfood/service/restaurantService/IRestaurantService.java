package com.example.donfood.service.restaurantService;

import com.example.donfood.dto.restaurantDTO.RestaurantRequestDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRestaurantService {
    RestaurantResponseDTO register(RestaurantRequestDTO restaurantRequestDTO);
    RestaurantResponseDTO update(Integer id, RestaurantUpdateDTO restaurantUpdateDTO);
    void delete(Integer id);
    List<RestaurantResponseDTO> getAll();
    RestaurantResponseDTO getById(Integer id);
    List<RestaurantResponseDTO> getByFullName(String fullname);

}
