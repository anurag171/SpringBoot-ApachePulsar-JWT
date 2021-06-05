package com.anurag.iot.data.api.security;



import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.anurag.iot.data.api.model.Role;
import com.anurag.iot.data.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * This is just an example, you can load the user from the database from the repository.
 * 
 */
@Service
@Slf4j
public class UserService {

    private Map<String, User> data;

    @PostConstruct
    public void init() {
        log.info("Initialize userservice");
        data = new HashMap<>();

        //username:passwowrd -> user:user
        data.put("user", new User("user", "nRWe2fM9UWavby7ZbU5+KYDaQ9sthDALs2jebqytJdE=", true, Arrays.asList(Role.ROLE_USER)));

        //username:passwowrd -> admin:admin
        data.put("admin", new User("admin", "nRWe2fM9UWavby7ZbU5+KYDaQ9sthDALs2jebqytJdE=", true, Arrays.asList(Role.ROLE_ADMIN)));
        log.info("Data initialized [{}]",data.toString());
    }

    public Mono<User> findByUsername(String username) {
        log.info("User received [{}] ",username);
        User user = data.get(username);
        log.info("user found [{}]",user);
        return Mono.justOrEmpty(user);
    }
}