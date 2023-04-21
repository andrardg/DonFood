
package com.example.donfood.component;
import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.mapper.AccountMapper;
import com.example.donfood.model.Account;
import com.example.donfood.model.enums.Right;
import com.example.donfood.repository.IAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@Component
@Profile("mysql")
public class DataLoader implements CommandLineRunner {

    private IAccountRepository accountRepository;

    private void loadUserData() {
        if (accountRepository.count() == 0){
            AccountRequestDTO adminRequest = AccountRequestDTO.builder()
                    .email("admin")
                    .passwordDecoded("pass")
                    .accountVerified(true)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .accessRights(Right.ADMIN)
                    .build();
            Account admin = AccountMapper.requestToAccount(adminRequest);
            accountRepository.save(admin);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
