package com.example.donfood.controller;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.dto.ongDTO.ONGResponseDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.model.Account;
import com.example.donfood.model.enums.Right;
import com.example.donfood.service.accoutService.AccountService;
import com.example.donfood.service.ongService.ONGService;
import com.example.donfood.service.restaurantService.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTests {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private ONGService ongService;

    @Test
    public void registerONG(){
        Account account = Account.builder()
                .email("ong100")
                .passwordEncoded("password100")
                .fullName("ONG100")
                .accountVerified(false)
                .accessRights(Right.ONG)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        ONGResponseDTO ongResponseDTO = ONGResponseDTO.builder()
                .ongId(1)
                .accountONG(account)
                .address("test")
                .nrPeopleHelping(1)
                .build();
        when(ongService.register(any())).thenReturn(ongResponseDTO);
        ResponseEntity<ONGResponseDTO> response = accountController.registerONG(any());
        assertNotNull(response);
        assertNotNull(response.getBody());
    }
    @Test
    public void registerRestaurant(){
        Account account = Account.builder()
                .email("ong100")
                .passwordEncoded("password100")
                .fullName("ONG100")
                .accountVerified(false)
                .accessRights(Right.ONG)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        RestaurantResponseDTO restaurantResponseDTO = RestaurantResponseDTO.builder()
                .restaurantId(1)
                .account(account)
                .socialScore(4.0)
                .build();
        when(restaurantService.register(any())).thenReturn(restaurantResponseDTO);
        ResponseEntity<RestaurantResponseDTO> response = accountController.registerRestaurant(any());
        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    @Test
    public void login(){
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .email("ong100")
                .passwordDecoded("password100")
                .build();
        Account account = Account.builder().build();
        BindingResult result = mock(BindingResult.class);

        when(accountService.login(any())).thenReturn(account);
        when(result.hasErrors()).thenReturn(false);

        ModelAndView response = accountController.login(accountRequestDTO, result);
        assertNotNull(response.getModel());
        //assertNotNull(response.getBody());
    }
}
