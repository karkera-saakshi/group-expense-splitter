package com.saakshi.expense_calculator.controller;
import com.saakshi.expense_calculator.dto.DetailsDto;
import com.saakshi.expense_calculator.enums.Direction;

import com.saakshi.expense_calculator.models.Owns;
import com.saakshi.expense_calculator.repositories.OwnsRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RestController
@CrossOrigin(origins = "*")
public class OwnsController {
    @Autowired
    OwnsRepo ownsRepo;
    @PostMapping("/enter-details")
    public void enterDetils(@RequestBody DetailsDto detailsDto)
    {
        Owns own = new Owns();
        if (detailsDto.getName() =="") {
            throw new IllegalArgumentException("Please enter name");
        }
        own.setOtherPartyName(detailsDto.getName());
        if (detailsDto.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        own.setAmount(detailsDto.getAmount());
        own.setCreatedAt(LocalDateTime.now());
        own.setPaid(false);
        ownsRepo.save(own);
    }

    @GetMapping("/histories/{userId}")
    public List<Owns> getHistory(@PathVariable int userId)
    {
        return ownsRepo.findAllByUser_Id(userId);
    }

    @GetMapping("/sort")
    public List<Owns> sort(@RequestParam String sortBy, @RequestParam String paid)
    {
        Sort sort;
        switch(sortBy)
        {
            case "amount_desc":
                sort = Sort.by("amount").ascending();
                break;

            case "amount_asec":
                sort = Sort.by("amount").ascending();
                break;

            case "time_asc":
                sort = Sort.by("createdAt").ascending();
                break;
            case "time_desc":
                sort = Sort.by("createdAt").descending();
                break;

            default:
                throw new IllegalArgumentException("Invalid sortBy value");
        }

        if (paid.equalsIgnoreCase("paid"))
        {
            return ownsRepo.findByPaid(true, sort);
        }
        else if (paid.equalsIgnoreCase("unpaid"))
        {
            return ownsRepo.findByPaid(false, sort);
        }
        else if (paid.equalsIgnoreCase("all")) {
            return ownsRepo.findAll(sort);
        }
        else{
            throw new IllegalArgumentException("Invalid paid value");
        }
    }
    @PatchMapping("/ispaid/{id}")
    public void ispaid(@PathVariable int id)
    {
        Owns own = ownsRepo.findById(id).orElseThrow(()->new RuntimeException("Record not found"));
        own.setPaid(true);
        ownsRepo.save(own);
    }

    @GetMapping("/direction")
    public List<Owns> byDirection(@RequestParam Direction direction) {
        return ownsRepo.findByDirection(direction);
    }
}
