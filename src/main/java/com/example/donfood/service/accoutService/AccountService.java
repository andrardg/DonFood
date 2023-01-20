package com.example.donfood.service.accoutService;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.dto.accountDTO.AccountUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.AccountMapper;
import com.example.donfood.model.Account;
import com.example.donfood.model.enums.Right;
import com.example.donfood.repository.IAccountRepository;
import com.example.donfood.repository.IONGRepository;
import com.example.donfood.repository.IRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IONGRepository ongRepository;
    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Override
    public Account register(Right right, AccountRequestDTO accountRequestDTO) {
        if(accountRequestDTO.getEmail() == null || accountRequestDTO.getPasswordDecoded() == null)
            throw new IllegalArgumentException("Email and password cannot be null");

        if(accountRepository.existsByEmail(accountRequestDTO.getEmail()))
            throw new IllegalArgumentException("Email is taken.");

        accountRequestDTO.setAccountVerified(false);
        accountRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRequestDTO.setAccessRights(right);
        Account account = AccountMapper.requestToAccount(accountRequestDTO);
        return account;
    }

    @Override
    public Account login(AccountRequestDTO accountRequestDTO) {
        if(accountRequestDTO.getEmail() == null || accountRequestDTO.getPasswordDecoded() == null)
            throw new IllegalArgumentException("Email and password cannot be null");

        Account dbAccount = accountRepository.findByEmail(accountRequestDTO.getEmail());
        if (dbAccount == null)
            throw new ResourceNotFoundException("Email or password incorrect");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean passwordIsValid = bCryptPasswordEncoder.matches(accountRequestDTO.getPasswordDecoded(), dbAccount.getPasswordEncoded());
        if (!passwordIsValid)
            throw new ResourceNotFoundException("Email or password incorrect");
        else
           return dbAccount;
    }

    @Override
    public Account update(String email, AccountUpdateDTO accountUpdateDTO) {
        Account dbAccount = accountRepository.findByEmail(email);
        if (dbAccount == null)
            throw new ResourceNotFoundException("Account was not found by email");
        // email is not editable

        if(accountUpdateDTO.getPasswordDecoded() != null){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            dbAccount.setPasswordEncoded(bCryptPasswordEncoder.encode(accountUpdateDTO.getPasswordDecoded()));
        }
        if(accountUpdateDTO.getFullName() != null)
            dbAccount.setFullName(accountUpdateDTO.getFullName());

        if(accountUpdateDTO.getAccessRights() != null)
            dbAccount.setAccessRights(accountUpdateDTO.getAccessRights());

        if(accountUpdateDTO.getAccountVerified() != null)
            dbAccount.setAccountVerified(accountUpdateDTO.getAccountVerified());

        return dbAccount;
    }

    @Override
    public void delete(String email) {
        if (email == null)
            throw new IllegalArgumentException("The email is null");
        if (!accountRepository.existsByEmail(email))
            throw new ResourceNotFoundException("The account with email: " + email + " was not found");

        Account account = accountRepository.findByEmail(email);
        if (ongRepository.existsByAccountONG(account))
            ongRepository.deleteByAccountONG(account);

        if(restaurantRepository.existsByAccountRest(account))
            restaurantRepository.deleteByAccountRest(account);

        accountRepository.deleteByEmail(email);
    }

//    @Override
//    public Account getByEmail(String email) {
//        if (email == null)
//            throw new IllegalArgumentException("The email is null");
//        Account dbAccount = accountRepository.findByEmail(email);
//        if (dbAccount == null)
//            throw new ResourceNotFoundException("Account was not found by email");
//        return dbAccount;
//    }
//
//    @Override
//    public Account getByFullName(String fullName) {
//        if (fullName.equals(""))
//            throw new IllegalArgumentException("The name is empty");
//        Optional<Account> dbAccount = accountRepository.findByFullName(fullName).stream().findFirst();
//        if (dbAccount.equals(Optional.empty()))
//            throw new ResourceNotFoundException("Account was not found by name");
//        return dbAccount.get();
//    }
}
