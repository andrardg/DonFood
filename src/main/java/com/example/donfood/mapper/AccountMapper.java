package com.example.donfood.mapper;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.model.Account;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AccountMapper {
    public static Account requestToAccount(AccountRequestDTO accountRequestDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountRequestDTO, Account.class);
        account.setPasswordEncoded(accountRequestDTO.getPasswordDecoded());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        account.setPasswordEncoded(bCryptPasswordEncoder.encode(accountRequestDTO.getPasswordDecoded()));
        return account;
    }
}
