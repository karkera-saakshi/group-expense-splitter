package com.saakshi.expense_calculator.dto;

import com.saakshi.expense_calculator.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsDto {
    String selfName;
    String otherPartyName;
    //boolean isPaid;
    double amount;
    Direction direction;
}
