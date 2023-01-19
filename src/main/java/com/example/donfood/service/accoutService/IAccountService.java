package com.example.donfood.service.accoutService;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.dto.accountDTO.AccountUpdateDTO;
import com.example.donfood.model.Account;
import com.example.donfood.model.enums.Right;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {
    Account register(Right right, AccountRequestDTO accountRequestDTO);
    Account login(AccountRequestDTO accountRequestDTO);
    Account update(String email, AccountUpdateDTO accountUpdateDTO);
    void delete(String email);
    //Account getByEmail(String email);
    //Account getByFullName(String fullName);
}
