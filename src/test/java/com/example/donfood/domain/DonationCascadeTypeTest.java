package com.example.donfood.domain;

import com.example.donfood.model.Account;
import com.example.donfood.model.Donation;
import com.example.donfood.model.Restaurant;
import com.example.donfood.model.enums.Measure;
import com.example.donfood.model.enums.Right;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

@DataJpaTest
@ActiveProfiles("h2")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class DonationCascadeTypeTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Disabled
    public void saveRestaurant() {
        Account account = Account.builder()
                .email("test")
                .passwordEncoded("adfda")
                .fullName("ana")
                .accessRights(Right.RESTAURANT)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .accountVerified(true)
                .build();
        Restaurant restaurant = Restaurant.builder()
                .accountRest(account)
                .build();

        Donation donation = new Donation().builder()
                .donationId(1)
                .restaurant(restaurant)
                .product("test")
                .quantity(1.0)
                .quantityMeasure(Measure.KG)
                .expirationDate(Timestamp.valueOf(LocalDateTime.now()))
                .pickUpLocation("1")
                .pickUpTime(new Time(0))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .modifiedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        restaurant.setDonations(Arrays.asList(donation));
        entityManager.persist(restaurant);
    }


    @Test
    public void updateRestaurant() {
        Donation donation = entityManager.find(Donation.class, 13);
        Restaurant restaurant = donation.getRestaurant();
        restaurant.setFiscalIdCode("1234");
        entityManager.merge(restaurant);
        entityManager.flush();
    }
}
