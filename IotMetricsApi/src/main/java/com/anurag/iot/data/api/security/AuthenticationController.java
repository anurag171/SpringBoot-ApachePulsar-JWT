package com.anurag.iot.data.api.security;

import com.anurag.iot.data.api.model.AuthRequest;
import com.anurag.iot.data.api.model.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@Slf4j
public class AuthenticationController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PBKDF2Encoder passwordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        log.info("Recieved message [{}]",ar.toString());
        log.info("password encoder [{}]",passwordEncoder ==null ? "empty":passwordEncoder);
        log.info("jwtutil [{}]",jwtUtil ==null ? "empty":jwtUtil);
        log.info("userService [{}]",userService ==null ? "empty":userService.findByUsername(ar.getUsername()));
        return userService.findByUsername(ar.getUsername())
            .filter(userDetails -> {log.info("userdetails in filter [{}]",userDetails); return passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword());})
            .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails))))
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

}