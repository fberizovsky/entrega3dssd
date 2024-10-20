package com.example.demo.repository;


import com.example.demo.models.PrincipalDeposit;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PrincipalDepositRepository extends JpaRepository<PrincipalDeposit, Long> {
    Optional<PrincipalDeposit> findByName(String name);
}