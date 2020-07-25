package com.javastart.customerservice.deposit.repository;

import com.javastart.customerservice.deposit.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    List<Deposit> findDepositByEmail(String email);
}
