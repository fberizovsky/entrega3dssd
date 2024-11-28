package com.example.demo.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ComunalDeposit;
import com.example.demo.models.Orden;
import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.enums.Estado;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {


   ArrayList<Orden> findByEstado(Estado estado);

   ArrayList<Orden> findByEstadoAndComunalDeposit(Estado estado, ComunalDeposit comunalDeposit);

   ArrayList<Orden> findByPrincipalDeposit(PrincipalDeposit depositoPrincipal);


}