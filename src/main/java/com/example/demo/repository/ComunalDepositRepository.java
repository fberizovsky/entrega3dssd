package com.example.demo.repository;

import com.example.demo.models.ComunalDeposit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComunalDepositRepository extends JpaRepository<ComunalDeposit, Long> {

    Optional<ComunalDeposit> findByEmail(String email);

    Optional<ComunalDeposit> findById(Long id);
}