package com.gestaodebibliotecas.gestaodebibliotecas.config;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.UserDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.User;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User("teste", "testetes@hotmail.com", "1111111111111111111111");
        User u2 = new User("dsadsadsa", "dsadsa@hotmail.com", "321321321321");

        userRepository.saveAll(Arrays.asList(u1, u2));
    }
}
