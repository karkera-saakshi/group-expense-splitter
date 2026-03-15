package com.saakshi.expense_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class ExpenseCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseCalculatorApplication.class, args);
	}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
                  {
        return new BCryptPasswordEncoder(10);
    }
}
