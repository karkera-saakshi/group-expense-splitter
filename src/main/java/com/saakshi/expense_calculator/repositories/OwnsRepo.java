package com.saakshi.expense_calculator.repositories;

import com.saakshi.expense_calculator.models.Owns;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saakshi.expense_calculator.enums.Direction;

import java.util.List;

@Repository
public interface OwnsRepo extends JpaRepository<Owns,Integer> {
    List<Owns> findAllByUser_Id(int userId);

    List<Owns> findByPaid(boolean b, Sort sort);

    List<Owns> findByDirection(Direction direction);
}
