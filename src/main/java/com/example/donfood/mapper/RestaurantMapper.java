package com.example.donfood.mapper;

import com.example.donfood.dto.restaurantDTO.RestaurantRequestDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantUpdateDTO;
import com.example.donfood.model.Restaurant;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMapper {
    private static AccountMapper accountMapper;

    public static Restaurant requestToRestaurant(RestaurantRequestDTO restaurantRequestDTO) {

        ModelMapper modelMapper = new ModelMapper();
        Restaurant restaurant = modelMapper.map(restaurantRequestDTO, Restaurant.class);
        return restaurant;
    }
    public static RestaurantResponseDTO restaurantToResponse(Restaurant restaurant) {

        ModelMapper modelMapper = new ModelMapper();
        RestaurantResponseDTO restaurantResponseDTO = modelMapper.map(restaurant, RestaurantResponseDTO.class);
        return  restaurantResponseDTO;
    }

    public static List<RestaurantResponseDTO> restaurantToResponseList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantMapper::restaurantToResponse)
                .collect(Collectors.toList());
    }
    public static RestaurantUpdateDTO responseToUpdate(RestaurantResponseDTO restaurantResponseDTO){
        ModelMapper modelMapper = new ModelMapper();
        RestaurantUpdateDTO restaurantUpdateDTO = modelMapper.map(restaurantResponseDTO, RestaurantUpdateDTO.class);
        restaurantUpdateDTO.setAccountUpdateDTO(AccountMapper.accountToUpdate(restaurantResponseDTO.getAccount()));
        return restaurantUpdateDTO;
    }
}
