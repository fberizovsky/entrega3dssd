package com.example.demo.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Colecta;
import com.example.demo.models.ComunalDeposit;

@Repository
public interface ColectaRepository extends JpaRepository<Colecta, Long> {

    ArrayList<Colecta> findByDepositoComunal(ComunalDeposit comunalDeposit);

}
