package com.example.donfood.service;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.dto.accountDTO.AccountUpdateDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantRequestDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.AccountMapper;
import com.example.donfood.model.Account;
import com.example.donfood.model.Restaurant;
import com.example.donfood.model.enums.Right;
import com.example.donfood.repository.IRestaurantRepository;
import com.example.donfood.service.accoutService.IAccountService;
import com.example.donfood.service.restaurantService.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTests {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private IAccountService accountService;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Test
    public void registerHappyFlow(){
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .email("test")
                .passwordDecoded("pass")
                .build();
        RestaurantRequestDTO restaurantRequestDTO = RestaurantRequestDTO.builder()
                .accountRequestDTO(accountRequestDTO)
                .build();

        accountRequestDTO.setAccountVerified(false);
        accountRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRequestDTO.setAccessRights(Right.RESTAURANT);
        Account account = AccountMapper.requestToAccount(accountRequestDTO);

        when(accountService.register(Right.RESTAURANT, accountRequestDTO)).thenReturn(account);
        assertNotNull(restaurantService.register(restaurantRequestDTO));
    }

    @Test
    public void registerPasswordNull(){
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .email("test")
                .passwordDecoded("")
                .build();
        RestaurantRequestDTO restaurantRequestDTO = RestaurantRequestDTO.builder()
                .accountRequestDTO(accountRequestDTO)
                .build();

        assertThrows(IllegalArgumentException.class, ()-> restaurantService.register(restaurantRequestDTO));
    }

    @Test
    public void updateHappyFlow(){
        Account account = Account.builder()
                .email("test")
                .build();
        Restaurant restaurant = Restaurant.builder()
                .accountRest(account)
                .build();
        AccountUpdateDTO accountUpdateDTO = AccountUpdateDTO.builder().build();
        RestaurantUpdateDTO restaurantUpdateDTO = RestaurantUpdateDTO.builder()
                .socialScore(3.0)
                .fiscalIdCode("abcdef")
                .accountUpdateDTO(accountUpdateDTO)
                .build();
        when(restaurantRepository.findByRestaurantId(1)).thenReturn(restaurant);
        assertNotNull(restaurantService.update(1, restaurantUpdateDTO));
    }

    @Test
    public void updateIdNotFound(){
        when(restaurantRepository.findByRestaurantId(1)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, ()-> restaurantService.update(1, any()));
    }

    @Test
    public void deleteHappyFlow(){
        when(restaurantRepository.existsById(1)).thenReturn(true);
        restaurantService.delete(1);
    }

    @Test
    public void deleteIdNull(){
        assertThrows(IllegalArgumentException.class, ()-> restaurantService.delete(null));
    }

    @Test
    public void deleteIdNotFound(){
        when(restaurantRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()->restaurantService.delete(1));
    }

    @Test
    public void getAllHappyFlow(){
        when(restaurantRepository.findAll()).thenReturn(Collections.emptyList());
        restaurantService.getAll();
    }

    @Test
    public void getByIdHappyFlow(){
        Account account = Account.builder()
                .email("test")
                .build();
        Restaurant restaurant = Restaurant.builder()
                .accountRest(account)
                .build();

        when(restaurantRepository.existsById(1)).thenReturn(true);
        when(restaurantRepository.getReferenceById(1)).thenReturn(restaurant);
        assertEquals(restaurant.getAccountRest().getEmail(), restaurantService.getById(1).getAccount().getEmail());
    }

    @Test
    public void getByIdNotFound(){
        when(restaurantRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()-> restaurantService.getById(1));
    }

    @Test
    public void getByFullNameHappyFlow(){
        Account account = Account.builder()
                .email("test")
                .fullName("name")
                .build();
        Restaurant restaurant = Restaurant.builder()
                .accountRest(account)
                .build();
        when(restaurantRepository.findByAccountRestFullName("name")).thenReturn(List.of(restaurant));
        assertEquals(List.of(restaurant).get(0).getAccountRest().getFullName(), restaurantService.getByFullName("name").get(0).getAccount().getFullName());
    }

    @Test
    public void getByFullNameNoResults(){
        when(restaurantRepository.findByAccountRestFullName("name")).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, ()->restaurantService.getByFullName("name"));
    }
}
