package com.thoughtworks.training.gateway1.controller;

import com.thoughtworks.training.gateway1.client.UserClientToGetUserId;
import com.thoughtworks.training.gateway1.dto.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;

@RestController
public class UserAPI {

    @Autowired
    UserClientToGetUserId userClientToGetUserId;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        String userName = user.getName();
        String password = user.getPassword();

        int userId = userClientToGetUserId.getUserIdByName(userName);

        String token = generateToken(userId,userName);

        return ResponseEntity.ok(token);
//        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public String generateToken(int userId,String userName) {
        //  User user = userRepository.findByName(userName).get();

        String secretKey = "kitty";

        HashMap<String, Object> claims = new HashMap<>();

//        System.out.println(user.getId());

        claims.put("userId", userId);
        claims.put("userName",userName);

        String token = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes(Charset.forName("UTF-8")))
                .compact();

        return token;
    }

}
