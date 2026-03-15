package com.saakshi.expense_calculator.controller;

import com.saakshi.expense_calculator.dto.CalculateRequestDto;
import com.saakshi.expense_calculator.dto.LoginDto;
import com.saakshi.expense_calculator.dto.PersonDto;
import com.saakshi.expense_calculator.models.User;
import com.saakshi.expense_calculator.repositories.UserRepo;
import com.saakshi.expense_calculator.services.ExpenseCalculationService;
import com.saakshi.expense_calculator.utils.JwtUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSOutput;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @PostMapping("/register")
    public String register(@RequestBody User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "Signup Successful";
    }

    @PostMapping("/login")
    public ResponseEntity<?>  login(@RequestBody LoginDto loginDto) throws Exception {
        User user = userRepo.findByUsername(loginDto.getUsername());
        if (user == null) {
            // Return plain text message, status 401
            return ResponseEntity.status(404).body("User not found");
        }

        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Incorrect password");
        }

        //Generate token and return it
        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "userId", user.getId(),
                "username", user.getUsername()
        ));

    }
    @Autowired
    ExpenseCalculationService expenseCalculationService;
    @PostMapping("/calculate")
    public Map<String, Double> generate(@RequestBody CalculateRequestDto requestDto)
    {
        return expenseCalculationService.calculateSplit(requestDto);
    }
}

