package com.saakshi.expense_calculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saakshi.expense_calculator.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsDto {
    String selfName;
    String otherPartyName;
    String otherPartyEmail;
    int reminderBefore;
    int reminderAfter;
    double amount;
    Direction direction;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dueDate;
}
