package com.example.donfood.service;
import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.dto.accountDTO.AccountUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.AccountMapper;
import com.example.donfood.model.Account;
import com.example.donfood.model.enums.Right;
import com.example.donfood.repository.IAccountRepository;
import com.example.donfood.repository.IONGRepository;
import com.example.donfood.repository.IRestaurantRepository;
import com.example.donfood.service.accoutService.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private IAccountRepository accountRepository;
    @Mock
    private IONGRepository ongRepository;
    @Mock
    private IRestaurantRepository restaurantRepository;

    @Test
    void registerHappyFlow(){
        AccountRequestDTO accountRequestDTOMock = AccountRequestDTO.builder()
                .email("ong100")
                .passwordDecoded("password100")
                .fullName("ONG100")
                .accountVerified(false)
                .accessRights(Right.ONG)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Account accountMock = AccountMapper.requestToAccount(accountRequestDTOMock);

        Account result = accountService.register(Right.ONG, accountRequestDTOMock);
        assertEquals(accountMock.getEmail(), result.getEmail());
    }

    @Test
    void registerEmailNull(){
        AccountRequestDTO accountRequestDTOMock = AccountRequestDTO.builder()
                .passwordDecoded("")
                .fullName("ONG100")
                .accountVerified(false)
                .accessRights(Right.ONG)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Account accountMock = AccountMapper.requestToAccount(accountRequestDTOMock);

        //Account result = accountService.register(Right.ONG, accountRequestDTOMock);
        assertThrows(IllegalArgumentException.class, () -> accountService.register(Right.ONG, accountRequestDTOMock));
    }

    @Test
    void registerEmailAlreadyExists(){
        AccountRequestDTO account = AccountRequestDTO.builder()
                .email("ong100")
                .passwordDecoded("password100")
                .fullName("ONG100")
                .accountVerified(false)
                .accessRights(Right.ONG)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        when(accountRepository.existsByEmail("ong100")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> accountService.register(Right.ONG, account));
    }

    @Test
    void loginHappyFlow(){
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .email("restaurant1@gmail.com")
                .passwordDecoded("ana")
                .build();

        Account dbAccount = AccountMapper.requestToAccount(accountRequestDTO);
        when(accountRepository.findByEmail("restaurant1@gmail.com")).thenReturn(dbAccount);
        assertEquals(accountService.login(accountRequestDTO).getEmail(), dbAccount.getEmail());
    }

    @Test
    public void loginEmailNotFound()
    {
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .email("restaurant1@gmail.com")
                .passwordDecoded("ana")
                .build();

        Account dbAccount = new Account();
        dbAccount.setPasswordEncoded("TEST");
        when(accountRepository.findByEmail("restaurant1@gmail.com")).thenReturn(dbAccount);

        assertThrows(ResourceNotFoundException.class, () -> accountService.login(accountRequestDTO));
    }

    @Test
    public void loginPasswordMismatch()
    {
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .email("restaurant1@gmail.com")
                .passwordDecoded("ana")
                .build();

        AccountRequestDTO dbAccountRequestDTO = AccountRequestDTO.builder()
                .email("restaurant1@gmail.com")
                .passwordDecoded("test")
                .build();

        Account dbAccount = AccountMapper.requestToAccount(dbAccountRequestDTO);
        when(accountRepository.findByEmail("restaurant1@gmail.com")).thenReturn(dbAccount);

        assertThrows(ResourceNotFoundException.class, () -> accountService.login(accountRequestDTO));
    }

    @Test
    public void loginEmailPasswordNull()
    {
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .build();

        assertThrows(IllegalArgumentException.class, () -> accountService.login(accountRequestDTO));
    }

    @Test
    void updateHappyFlow(){
        String email = "restaurant1@gmail.com";
        AccountUpdateDTO accountUpdateDTO = AccountUpdateDTO.builder()
                .passwordDecoded("ana")
                .accountVerified(true)
                .fullName("Ana")
                .accessRights(Right.RESTAURANT)
                .build();

        Account dbAccount = Account.builder()
                .email("restaurant1@gmail.com")
                .passwordEncoded("test")
                .accountVerified(false)
                .fullName("test")
                .accessRights(Right.ONG)
                .build();
        when(accountRepository.findByEmail("restaurant1@gmail.com")).thenReturn(dbAccount);
        assertEquals(dbAccount, accountService.update(email, accountUpdateDTO));
    }

    @Test
    public void updateEmailNotFound()
    {
        String email = "restaurant1@gmail.com";
        AccountUpdateDTO accountUpdateDTO = AccountUpdateDTO.builder()
                .passwordDecoded("ana")
                .accountVerified(true)
                .fullName("Ana")
                .accessRights(Right.RESTAURANT)
                .build();

        when(accountRepository.findByEmail("restaurant1@gmail.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> accountService.update(email, accountUpdateDTO));
    }

    @Test
    public void deleteONGHappyFlow()
    {
        String email = "email";
        when(accountRepository.existsByEmail(email)).thenReturn(true);
        Account account = Account.builder()
                .email(email)
                .passwordEncoded("test")
                .build();
        when(accountRepository.findByEmail(email)).thenReturn(account);
        when(ongRepository.existsByAccountONG(account)).thenReturn(true);
        when(restaurantRepository.existsByAccountRest(account)).thenReturn(false);
        accountService.delete(email);
    }

    @Test
    public void deleteRestaurantHappyFlow()
    {
        String email = "email";
        when(accountRepository.existsByEmail(email)).thenReturn(true);
        Account account = Account.builder()
                .email(email)
                .passwordEncoded("test")
                .build();
        when(accountRepository.findByEmail(email)).thenReturn(account);
        when(ongRepository.existsByAccountONG(account)).thenReturn(false);
        when(restaurantRepository.existsByAccountRest(account)).thenReturn(true);
        accountService.delete(email);
    }

    @Test
    public void deleteEmailIsNull()
    {
        String email = "";
        assertThrows(IllegalArgumentException.class, () -> accountService.delete(null));
    }
    @Test
    public void deleteEmailNotFound()
    {
        assertThrows(ResourceNotFoundException.class, () -> accountService.delete(""));
    }
}
