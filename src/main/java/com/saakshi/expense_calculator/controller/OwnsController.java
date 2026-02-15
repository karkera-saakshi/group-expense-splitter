package com.saakshi.expense_calculator.controller;
import com.saakshi.expense_calculator.dto.DetailsDto;
import com.saakshi.expense_calculator.enums.Direction;

import com.saakshi.expense_calculator.models.Owns;
import com.saakshi.expense_calculator.models.User;
import com.saakshi.expense_calculator.repositories.OwnsRepo;
import com.saakshi.expense_calculator.repositories.UserRepo;
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
    @Autowired
    UserRepo userRepo;
    @PostMapping("/enter-details")
    public void enterDetils(@RequestBody DetailsDto detailsDto)
    {
        User user = userRepo.findByUsername(detailsDto.getSelfName());
        if (user == null) {
            throw new IllegalArgumentException("User not found with username: " + detailsDto.getSelfName());
        }
        Owns own = new Owns();
        own.setUser(user);

        if (detailsDto.getSelfName() == null || detailsDto.getSelfName().trim().isEmpty())
        {
            throw new IllegalArgumentException("Please enter name");
        }
        if (detailsDto.getOtherPartyName() == null || detailsDto.getOtherPartyName().trim().isEmpty())
        {
            throw new IllegalArgumentException("Please enter name");
        }
        own.setOtherPartyName(detailsDto.getOtherPartyName());
        own.setSelfName(detailsDto.getSelfName());
        if (detailsDto.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        own.setAmount(detailsDto.getAmount());
        own.setCreatedAt(LocalDateTime.now());
        own.setPaid(false);
        own.setUser(user);  // MUST set this
        own.setDirection(detailsDto.getDirection());

        ownsRepo.save(own);
    }

    @GetMapping("/histories/{userId}")
    public List<Owns> getHistory(@PathVariable int userId)
    {
        return ownsRepo.findAllByUser_Id(userId);
    }

    @GetMapping("/sort")
    public List<Owns> sort(
            @RequestParam Long userId,
            @RequestParam String sortBy1,
            @RequestParam String sortBy2,
            @RequestParam String paid) {

        // --- 1. Handle first sort (amount) ---
        Sort sort1;
        switch (sortBy1.toLowerCase()) {
            case "amount_desc":
                sort1 = Sort.by("amount").descending();
                break;
            case "amount_asc":
            case "default":
                sort1 = Sort.by("amount").ascending();
                break;
            default:
                sort1 = Sort.by("amount").ascending();
        }

        // --- 2. Handle second sort (time) ---
        Sort sort2;
        switch (sortBy2.toLowerCase()) {
            case "time_desc":
                sort2 = Sort.by("createdAt").descending();
                break;
            case "time_asc":
                sort2 = Sort.by("createdAt").ascending();
                break;
            default:
                sort2 = Sort.by("createdAt").descending();
        }

        // --- 3. Combine both sorts ---
        Sort combinedSort = sort2.and(sort1);

        // --- 4. Filter by paid status and user ---
        if (paid.equalsIgnoreCase("paid")) {
            return ownsRepo.findByUserIdAndPaid(userId, true, combinedSort);
        } else if (paid.equalsIgnoreCase("unpaid")) {
            return ownsRepo.findByUserIdAndPaid(userId, false, combinedSort);
        } else if (paid.equalsIgnoreCase("all")) {
            return ownsRepo.findByUserId(userId, combinedSort);
        } else {
            throw new IllegalArgumentException("Invalid paid value: " + paid);
        }
    }

    @DeleteMapping("/delete/{userId}/{historyId}")
    public void delete(@PathVariable int userId, @PathVariable int historyId)
    {
        User user = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        Owns own = ownsRepo.findById(historyId).orElseThrow(()-> new RuntimeException("Transaction not found"));
        ownsRepo.delete(own);
    }

    @PatchMapping("/status/{id}")
    public void updateStatus(@PathVariable int id, @RequestParam boolean paid) {

        Owns own = ownsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        own.setPaid(paid);
        ownsRepo.save(own);
    }


    @GetMapping("/direction")
    public List<Owns> byDirection(@RequestParam Direction direction) {
        return ownsRepo.findByDirection(direction);
    }
}
