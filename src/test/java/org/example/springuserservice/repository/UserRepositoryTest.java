package org.example.springuserservice.repository;

import org.example.springuserservice.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("TestDB")
            .withUsername("testlog")
            .withPassword("testpassword");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFind() {
        User user = User.builder()
                .name("Test user")
                .email("test@mail.com")
                .age(27)
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);

        Optional<User> found = userRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Test user", found.get().getName());
    }

    @Test
    void testDelete() {
        User user = User.builder()
                .name("Anna")
                .email("anna@mail.com")
                .age(23)
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);
        userRepository.deleteById(saved.getId());

        assertFalse(userRepository.findById(saved.getId()).isPresent());
    }
}
