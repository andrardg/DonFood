package com.example.donfood.service;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.dto.accountDTO.AccountUpdateDTO;
import com.example.donfood.dto.ongDTO.ONGRequestDTO;
import com.example.donfood.dto.ongDTO.ONGUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.AccountMapper;
import com.example.donfood.model.Account;
import com.example.donfood.model.ONG;
import com.example.donfood.model.enums.Right;
import com.example.donfood.repository.IONGRepository;
import com.example.donfood.service.accoutService.IAccountService;
import com.example.donfood.service.ongService.ONGService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ONGServiceTests {

    @InjectMocks
    private ONGService ongService;

    @Mock
    private IAccountService accountService;

    @Mock
    private IONGRepository ongRepository;

    @Test
    public void registerHappyFlow(){
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .email("test")
                .passwordDecoded("pass")
                .build();
        ONGRequestDTO ongRequestDTO = ONGRequestDTO.builder()
                .accountRequestDTO(accountRequestDTO)
                .address("address")
                .nrPeopleHelping(1)
                .build();
        accountRequestDTO.setAccountVerified(false);
        accountRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRequestDTO.setAccessRights(Right.ONG);

        Account account = AccountMapper.requestToAccount(accountRequestDTO);
        when(accountService.register(Right.ONG, accountRequestDTO)).thenReturn(account);
        assertNotNull(ongService.register(ongRequestDTO));
    }

    @Test
    public void registerPasswordNull(){
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .email("test")
                .passwordDecoded("")
                .build();
        ONGRequestDTO ongRequestDTO = ONGRequestDTO.builder()
                .accountRequestDTO(accountRequestDTO)
                .address("address")
                .nrPeopleHelping(1)
                .build();
        assertThrows(IllegalArgumentException.class, ()->ongService.register(ongRequestDTO));
    }

    @Test
    public void updateHappyFlow(){
        AccountUpdateDTO accountUpdateDTO = AccountUpdateDTO.builder().build();
        Account account = new Account();
        account.setEmail("test");

        ONG ong = new ONG();
        ong.setAccountONG(account);

        ONGUpdateDTO ongUpdateDTO = ONGUpdateDTO.builder()
                .address("test")
                .nrPeopleHelping(1)
                .accountUpdateDTO(accountUpdateDTO)
                .build();

        when(ongRepository.findByOngId(1)).thenReturn(ong);
        when(accountService.update(account.getEmail(), accountUpdateDTO)).thenReturn(account);
        assertNotNull(ongService.update(1, ongUpdateDTO));
    }

    @Test
    public void updateIdNotFound(){
        AccountUpdateDTO accountUpdateDTO = AccountUpdateDTO.builder().build();
        ONGUpdateDTO ongUpdateDTO = ONGUpdateDTO.builder()
                .address("test")
                .nrPeopleHelping(1)
                .accountUpdateDTO(accountUpdateDTO)
                .build();
        when(ongRepository.findByOngId(1)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, ()-> ongService.update(1,ongUpdateDTO));
    }

    @Test
    public void deleteHappyFlow(){
        when(ongRepository.existsById(1)).thenReturn(true);
        ongService.delete(1);
    }

    @Test
    public void deleteIdNull(){
        assertThrows(IllegalArgumentException.class, ()-> ongService.delete(null));
    }

    @Test
    public void deleteIdNotFound(){
        when(ongRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()-> ongService.delete(1));
    }

    @Test
    public void getAllHappyFlow(){
        when(ongRepository.findAll()).thenReturn(Collections.emptyList());
        ongService.getAll();
    }

    @Test
    public void getByIdHappyFlow(){
        when(ongRepository.existsById(1)).thenReturn(true);

        Account account = new Account();
        account.setEmail("test");

        ONG ong = new ONG();
        ong.setOngId(1);
        ong.setAccountONG(account);

        when(ongRepository.getReferenceById(1)).thenReturn(ong);
        assertNotNull(ongService.getById(1));
    }

    @Test
    public void getByIdNotFound(){
        when(ongRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()-> ongService.getById(1));
    }

    @Test
    public void getByFullNameHappyFlow(){

        Account account = new Account();
        account.setFullName("test");
        account.setEmail("test");

        ONG ong = new ONG();
        ong.setAccountONG(account);

        when(ongRepository.findByAccountONGFullName("test")).thenReturn(List.of(ong));
        assertNotNull(ongService.getByFullName("test"));
    }

    @Test
    public void getByFullNameNoneFound(){
        when(ongRepository.findByAccountONGFullName("test")).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, ()-> ongService.getByFullName("test"));
    }
}
