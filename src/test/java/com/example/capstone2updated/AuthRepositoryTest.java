package com.example.capstone2updated;


import com.example.capstone2updated.Model.SalesPerson;
import com.example.capstone2updated.Model.User;
import com.example.capstone2updated.Repository.AuthRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthRepositoryTest {

    @Autowired
    AuthRepository authRepository;


    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        // clean the table
        authRepository.deleteAll();

        user1 = new User();

        user1.setUsername("abdullah");
        user1.setRole("ADMIN");
        user1.setPassword("12345678910Aa!@#$");
        user1.setEmail("abdullah@saudi.sa");


        user2 = new User();

        user2.setUsername("saleh");
        user2.setRole("ADMIN");
        user2.setPassword("12345678910Aa!@#$");
        user2.setEmail("saleh@saudi.sa");
    }

    @Test
    void findUserByUsernameTest() {
        authRepository.save(user1);
        authRepository.save(user2);


        User user = authRepository.findUserByUsername("abdullah");

        Assertions.assertThat(user.getUsername()).isEqualTo(user1.getUsername());
    }


    @Test
    void findUserByEmailTest() {
        authRepository.save(user1);
        authRepository.save(user2);


        User user = authRepository.findUserByEmail("saleh@saudi.sa");

        Assertions.assertThat(user.getEmail()).isEqualTo(user2.getEmail());
    }
}
